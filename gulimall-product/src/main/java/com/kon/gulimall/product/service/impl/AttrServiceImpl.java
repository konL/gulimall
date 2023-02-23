package com.kon.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kon.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.kon.gulimall.product.dao.AttrGroupDao;
import com.kon.gulimall.product.dao.CategoryDao;
import com.kon.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.kon.gulimall.product.entity.AttrGroupEntity;
import com.kon.gulimall.product.entity.CategoryEntity;
import com.kon.gulimall.product.service.AttrAttrgroupRelationService;
import com.kon.gulimall.product.service.AttrGroupService;
import com.kon.gulimall.product.service.CategoryService;
import com.kon.gulimall.product.vo.AttrResVo;
import com.kon.gulimall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
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

import com.kon.gulimall.product.dao.AttrDao;
import com.kon.gulimall.product.entity.AttrEntity;
import com.kon.gulimall.product.service.AttrService;
import org.springframework.transaction.annotation.Transactional;

import javax.smartcardio.ATR;


@Service("attrService")
public class AttrServiceImpl extends ServiceImpl<AttrDao, AttrEntity> implements AttrService {
    @Autowired
    AttrGroupDao attrGroupDao;
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                new QueryWrapper<AttrEntity>()
        );

        return new PageUtils(page);
    }

    @Autowired
    AttrAttrgroupRelationDao relationDao;

    @Transactional
    @Override
    public void saveAttr(AttrVo attr) {
        //保存基本信息到attr
        //保存基本信息以及对应的分组信息到关联表

        AttrEntity attrEntity= new AttrEntity();
        BeanUtils.copyProperties(attr,attrEntity);
        this.save(attrEntity);

        AttrAttrgroupRelationEntity entity=new AttrAttrgroupRelationEntity();
        entity.setAttrGroupId(attr.getAttrGroupId());
        entity.setAttrId(attrEntity.getAttrId());
        relationDao.insert(entity);

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId) {
        //根据catelogId查询列表数据
        QueryWrapper<AttrEntity> wrapper=new QueryWrapper<AttrEntity>();
        if(catelogId!=0){
            wrapper.eq("catelog_id",catelogId);
        }

        //判断模糊搜索
        String key= (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            query().and((w)->{
               w.eq("attr_id",key).or().like("attr_name",key);
            });
        }
        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                wrapper
        );

        //目前，从attr表中获得了对应的数据，但是这个表下只有groupId而没有name
        //避免去关联查询
        //所以获得这个数据之后，通过流继续操作其他表
        PageUtils data=new PageUtils(page);
        List<AttrEntity> records=page.getRecords();
        //根据列表中的id获取对应的分组名字或分类
        List<AttrResVo> respVos=records.stream().map((attrEntity) -> {
            AttrResVo attrResVo=new AttrResVo();
            BeanUtils.copyProperties(attrEntity,attrResVo);
            //分组
            AttrAttrgroupRelationEntity attr=relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id",attrEntity.getAttrId()));
            if(attr!=null){
                AttrGroupEntity attrGroupEntity=attrGroupDao.selectById(attr.getAttrGroupId());
                attrResVo.setGroupName(attrGroupEntity.getAttrGroupName());

            }
            //分类
            CategoryEntity categoryEntity=categoryDao.selectById(attrEntity.getCatelogId());
            if(categoryEntity!=null){
                attrResVo.setCatelogName(categoryEntity.getName());
            }


            return attrResVo;

        }).collect(Collectors.toList());

        data.setList(respVos);
        return data;
    }

    @Autowired
    CategoryService categoryService;

    @Override
    public AttrResVo getAttrInfo(Long attrId) {
        //基本信息
        AttrEntity attrEntity = this.getById(attrId);
        //增加部分信息

        AttrResVo attrResVo=new AttrResVo();
        BeanUtils.copyProperties(attrEntity,attrResVo);
        attrResVo.setValueType(attrEntity.getValueType());

        //设置分组信息
        AttrAttrgroupRelationEntity relationEntity = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrId));
        if(relationEntity!=null){
            attrResVo.setAttrGroupId(relationEntity.getAttrGroupId());

            AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(relationEntity.getAttrGroupId());
            if(attrGroupEntity!=null) {
                attrResVo.setGroupName(attrGroupEntity.getAttrGroupName());
            }

        }

        //设置分类信息
        Long catelogId=attrEntity.getCatelogId();
        Long[] catelogPath=categoryService.findCatelogPath(catelogId);
        attrResVo.setCatelogPath(catelogPath);
        CategoryEntity categoryEntity = categoryDao.selectById(catelogId);
        if(categoryEntity!=null) {
            attrResVo.setCatelogName(categoryEntity.getName());
        }


        return attrResVo;
    }

    @Override
    public void updateAttr(AttrVo attr) {
        //关联去保存
        AttrEntity attrEntity = new AttrEntity();
        BeanUtils.copyProperties(attr, attrEntity);
        // 更新自己
        this.updateById(attrEntity);
        AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
        // 更新关联关系
        if (attr.getAttrGroupId() != null){

            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            attrAttrgroupRelationService.update(relationEntity, new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
        }

        //判断新增还是修改
        Integer count=relationDao.selectCount(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
        if(count>0){
            //修改
            relationDao.update(relationEntity, new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));

        }else{
            //增加
            relationDao.insert(relationEntity);


        }
    }


}