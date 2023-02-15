package com.kon.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.product.entity.CommentReplayEntity;

import java.util.Map;

/**
 * 商品评价回复关系
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:16
 */
public interface CommentReplayService extends IService<CommentReplayEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

