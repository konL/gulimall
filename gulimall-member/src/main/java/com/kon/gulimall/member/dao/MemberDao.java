package com.kon.gulimall.member.dao;

import com.kon.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:10:16
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
