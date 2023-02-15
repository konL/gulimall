package com.kon.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.order.entity.OrderEntity;

import java.util.Map;

/**
 * 订单
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:06:57
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

