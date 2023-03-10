package com.kon.gulimall.order.dao;

import com.kon.gulimall.order.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:06:57
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
