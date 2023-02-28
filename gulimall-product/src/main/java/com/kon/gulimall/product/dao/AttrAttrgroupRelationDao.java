package com.kon.gulimall.product.dao;

import com.kon.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 属性&属性分组关联
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:18
 */
@Mapper
public interface AttrAttrgroupRelationDao extends BaseMapper<AttrAttrgroupRelationEntity> {

    void deleteBatchRelations(@Param("relationEntityList") List<AttrAttrgroupRelationEntity> relationEntityList);

    void deleteBatchRelation(@Param("entities") List<AttrAttrgroupRelationEntity> entities);

}
