package com.kon.gulimall.coupon.dao;

import com.kon.gulimall.coupon.entity.CouponEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 优惠券信息
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-06 23:50:57
 */
@Mapper
public interface CouponDao extends BaseMapper<CouponEntity> {
	
}
