package com.kon.gulimall.ware.dao;

import com.kon.gulimall.ware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:12:00
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
