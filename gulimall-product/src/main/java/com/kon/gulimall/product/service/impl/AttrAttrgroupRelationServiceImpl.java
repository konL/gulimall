package com.kon.gulimall.product.service.impl;

import com.kon.gulimall.product.vo.AttrGroupRelationVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kon.common.utils.PageUtils;
import com.kon.common.utils.Query;

import com.kon.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.kon.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.kon.gulimall.product.service.AttrAttrgroupRelationService;
import org.w3c.dom.Attr;


@Service("attrAttrgroupRelationService")
public class AttrAttrgroupRelationServiceImpl extends ServiceImpl<AttrAttrgroupRelationDao, AttrAttrgroupRelationEntity> implements AttrAttrgroupRelationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrAttrgroupRelationEntity> page = this.page(
                new Query<AttrAttrgroupRelationEntity>().getPage(params),
                new QueryWrapper<AttrAttrgroupRelationEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    AttrAttrgroupRelationDao attrAttrgroupRelationDao;
    @Override
    public void deleteBatchRelation(List<AttrAttrgroupRelationEntity> relationEntityList) {
        attrAttrgroupRelationDao.deleteBatchRelations(relationEntityList);
    }

    @Override
    public void saveRelationBatch(List<AttrGroupRelationVo> attrGroupRelationVoList) {
        // 使用Stream流将接收到的属性放到数据库对应实体类中。
        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntityList = attrGroupRelationVoList.stream().map(item -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        // 批量保存
        this.saveBatch(attrAttrgroupRelationEntityList);
    }

}