package com.kon.gulimall.product;


import com.kon.gulimall.product.entity.BrandEntity;
import com.kon.gulimall.product.service.BrandService;
import com.kon.gulimall.product.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
class GulimallProductApplicationTests {

    @Autowired
    BrandService brandService;

    @Autowired
    CategoryService categoryService;

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

    @Test
    public void testFindPath(){
        Long[] catelogPath=categoryService.findCatelogPath(225L);
        log.info("完整路径：{}", Arrays.asList(catelogPath));
    }

}
