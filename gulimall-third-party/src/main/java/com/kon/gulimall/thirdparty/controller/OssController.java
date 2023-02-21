package com.kon.gulimall.thirdparty.controller;


import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.kon.common.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController

@RefreshScope
@RequestMapping("/thirdparty")
public class OssController {



    @Autowired(required = false)

    OSS ossClient;



    @Value("${spring.cloud.alicloud.access-key}")

    private String accessId;

    @Value("${spring.cloud.alicloud.secret-key}")

    private String accessKey;

    @Value("${spring.cloud.alicloud.oss.endpoint}")

    private String endpoint;

    @Value("${spring.cloud.alicloud.oss.bucket}")

    private String bucket;



    @RequestMapping("/oss/policy")

    public R policy() {

        // 填写Host地址，格式为https://bucketname.endpoint。
        String host = String.format("https://%s.%s", bucket, endpoint);

        // 设置上传回调URL，即回调服务器地址，用于处理应用服务器与OSS之间的通信。OSS会在文件上传完成后，把文件上传信息通过此回调URL发送给应用服务器。

//        String callbackUrl = "https://192.168.0.0:8888";

        //以日期为目录
        String dir = LocalDate.now().toString() + "/";



        Map<String, String> respMap = null;

        try {

            long expireTime = 30;

            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;

            Date expiration = new Date(expireEndTime);

            PolicyConditions policyConds = new PolicyConditions();

            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);

            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, dir);



            String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);

            byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);

            String encodedPolicy = BinaryUtil.toBase64String(binaryData);

            String postSignature = ossClient.calculatePostSignature(postPolicy);



            respMap = new LinkedHashMap<String, String>();

            respMap.put("accessid", accessId);

            respMap.put("policy", encodedPolicy);

            respMap.put("signature", postSignature);

            respMap.put("dir", dir);

            respMap.put("host", host);

            respMap.put("expire", String.valueOf(expireEndTime / 1000));



        } catch (Exception e) {

            // Assert.fail(e.getMessage());

            System.out.println(e.getMessage());

        }

        return R.ok().put("data",respMap);

    }

}
