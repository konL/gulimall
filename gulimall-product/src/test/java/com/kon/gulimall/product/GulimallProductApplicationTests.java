package com.kon.gulimall.product;

import com.kon.gulimall.product.entity.BrandEntity;
import com.kon.gulimall.product.service.BrandService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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

}
