package com.kon.gulimall.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.order.entity.PaymentInfoEntity;

import java.util.Map;

/**
 * 支付信息表
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:06:57
 */
public interface PaymentInfoService extends IService<PaymentInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

