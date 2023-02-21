package com.kon.gulimall.product;


import com.kon.gulimall.product.entity.BrandEntity;
import com.kon.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Test
    void contextLoads() {
        BrandEntity brandEntity=new BrandEntity();
        brandEntity.setName("iphone");

        brandService.save(brandEntity);
        System.out.println("插入成功");

    }


//    @Resource
//    OSSClient ossClient;

    @Test
    void testFileUpload() throws FileNotFoundException {
//       InputStream inputStream=new FileInputStream("C:\\Users\\liang\\Pictures\\test.jpg");
//       ossClient.putObject("gulimall-kon","test.jpg",inputStream);
//       ossClient.shutdown();
//       System.out.println("上传完成");

    }

}
