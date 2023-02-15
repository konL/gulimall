package com.kon.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.product.entity.SkuImagesEntity;

import java.util.Map;

/**
 * sku图片
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:15
 */
public interface SkuImagesService extends IService<SkuImagesEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

