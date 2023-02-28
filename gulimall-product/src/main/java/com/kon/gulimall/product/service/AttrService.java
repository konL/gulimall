package com.kon.gulimall.product.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kon.common.utils.PageUtils;
import com.kon.gulimall.product.entity.AttrEntity;
import com.kon.gulimall.product.vo.AttrGroupRelationVo;
import com.kon.gulimall.product.vo.AttrResVo;
import com.kon.gulimall.product.vo.AttrVo;

import java.util.List;
import java.util.Map;

/**
 * 商品属性
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:18
 */
public interface AttrService extends IService<AttrEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveAttr(AttrVo attr);

    PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId,String type);

    AttrResVo getAttrInfo(Long attrId);


    void updateAttr(AttrVo attr);

    List<AttrEntity> getRelationAttr(String attrgroupId);

    void deleteRelation(AttrGroupRelationVo[] vos);

    PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params);
}

