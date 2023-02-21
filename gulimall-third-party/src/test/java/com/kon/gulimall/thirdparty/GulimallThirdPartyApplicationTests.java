package com.kon.gulimall.thirdparty;

import com.aliyun.oss.OSSClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallThirdPartyApplicationTests {

    @Resource
    OSSClient ossClient;

    @Test
    void testFileUpload() throws FileNotFoundException {
        InputStream inputStream=new FileInputStream("C:\\Users\\liang\\Pictures\\test.jpg");
        ossClient.putObject("gulimall-kon","hahha.jpg",inputStream);
        ossClient.shutdown();
        System.out.println("上传完成");

    }

}
