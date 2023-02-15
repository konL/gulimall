package com.kon.gulimall.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.member.entity.MemberLoginLogEntity;

import java.util.Map;

/**
 * 会员登录记录
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:10:16
 */
public interface MemberLoginLogService extends IService<MemberLoginLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

