package com.kon.gulimall.product.dao;

import com.kon.gulimall.product.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:17
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
