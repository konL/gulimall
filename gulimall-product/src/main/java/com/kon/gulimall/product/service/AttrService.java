package com.kon.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.product.entity.AttrEntity;

import java.util.Map;

/**
 * 商品属性
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:18
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

