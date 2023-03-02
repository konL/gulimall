package com.kon.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kon.common.constant.ProductConstant;
import com.kon.gulimall.product.dao.AttrAttrgroupRelationDao;
import com.kon.gulimall.product.dao.AttrGroupDao;
import com.kon.gulimall.product.dao.CategoryDao;
import com.kon.gulimall.product.entity.AttrAttrgroupRelationEntity;
import com.kon.gulimall.product.entity.AttrGroupEntity;
import com.kon.gulimall.product.entity.CategoryEntity;
import com.kon.gulimall.product.service.AttrAttrgroupRelationService;
import com.kon.gulimall.product.service.AttrGroupService;
import com.kon.gulimall.product.service.CategoryService;
import com.kon.gulimall.product.vo.AttrGroupRelationVo;
import com.kon.gulimall.product.vo.AttrResVo;
import com.kon.gulimall.product.vo.AttrRespVo;
import com.kon.gulimall.product.vo.AttrVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
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
        AttrEntity attrEntity = new AttrEntity();
//        attrEntity.setAttrName(attr.getAttrName());
        BeanUtils.copyProperties(attr,attrEntity);
        //1、保存基本数据
        this.save(attrEntity);
        //2、保存关联关系
        if(attr.getAttrType() == ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode() && attr.getAttrGroupId()!=null){
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            relationEntity.setAttrGroupId(attr.getAttrGroupId());
            relationEntity.setAttrId(attrEntity.getAttrId());
            relationDao.insert(relationEntity);
        }

    }

    @Override
    public PageUtils queryBaseAttrPage(Map<String, Object> params, Long catelogId, String type) {
        QueryWrapper<AttrEntity> queryWrapper = new QueryWrapper<AttrEntity>().eq("attr_type","base".equalsIgnoreCase(type)?ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode():ProductConstant.AttrEnum.ATTR_TYPE_SALE.getCode());

        if(catelogId != 0){
            queryWrapper.eq("catelog_id",catelogId);
        }

        String key = (String) params.get("key");
        if(!StringUtils.isEmpty(key)){
            //attr_id  attr_name
            queryWrapper.and((wrapper)->{
                wrapper.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(
                new Query<AttrEntity>().getPage(params),
                queryWrapper
        );

        PageUtils pageUtils = new PageUtils(page);
        List<AttrEntity> records = page.getRecords();
        List<AttrRespVo> respVos = records.stream().map((attrEntity) -> {
            AttrRespVo attrRespVo = new AttrRespVo();
            BeanUtils.copyProperties(attrEntity, attrRespVo);

            //1、设置分类和分组的名字
            if("base".equalsIgnoreCase(type)){
                AttrAttrgroupRelationEntity attrId = relationDao.selectOne(new QueryWrapper<AttrAttrgroupRelationEntity>().eq("attr_id", attrEntity.getAttrId()));
                if (attrId != null && attrId.getAttrGroupId()!=null) {
                    AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrId.getAttrGroupId());
                    attrRespVo.setGroupName(attrGroupEntity.getAttrGroupName());
                }

            }


            CategoryEntity categoryEntity = categoryDao.selectById(attrEntity.getCatelogId());
            if (categoryEntity != null) {
                attrRespVo.setCatelogName(categoryEntity.getName());
            }
            return attrRespVo;
        }).collect(Collectors.toList());

        pageUtils.setList(respVos);
        return pageUtils;
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

    /**
     * 根据分组Id查找关联的所有属性
     * @param attrgroupId
     * @return
     */

    @Override
    public List<AttrEntity> getRelationAttr(Long attrgroupId) {
        QueryWrapper<AttrAttrgroupRelationEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("attr_group_id", attrgroupId);
        List<AttrAttrgroupRelationEntity> list = attrAttrgroupRelationService.list(wrapper);
        // 使用Stream流来快速获取该分组下的所有属性id
        List<Long> attrIdList=new ArrayList<>();
        if(list!=null) {
            attrIdList= list.stream().map(AttrAttrgroupRelationEntity::getAttrId).collect(Collectors.toList());
        }
        return this.listByIds(attrIdList);    }

    @Override
    public void deleteRelation(AttrGroupRelationVo[] vos) {
        //relationDao.delete(new QueryWrapper<>().eq("attr_id",1L).eq("attr_group_id",1L));
        //
        List<AttrAttrgroupRelationEntity> entities = Arrays.asList(vos).stream().map((item) -> {
            AttrAttrgroupRelationEntity relationEntity = new AttrAttrgroupRelationEntity();
            BeanUtils.copyProperties(item, relationEntity);
            return relationEntity;
        }).collect(Collectors.toList());
        relationDao.deleteBatchRelation(entities);
    }


    /**
     * 获取当前分组没有关联的属性
     * @param attrgroupId
     * @param params
     * @return
     */

    @Override
    public PageUtils getNoRelationAttr(Long attrgroupId, Map<String, Object> params) {
        //只关联自己所属分类属性
        //查出分组所在分类
        AttrGroupEntity attrGroupEntity = attrGroupDao.selectById(attrgroupId);
        Long catelogId = attrGroupEntity.getCatelogId();
        //2\别的分组没有引用的属性
        //2.1当前分类其他分组
        //2，2 其关联的属性
        List<AttrGroupEntity> group = attrGroupDao.selectList(new QueryWrapper<AttrGroupEntity>().eq("catelog_id", catelogId));
        List<Long> groupIds = group.stream().map((item) -> {
            return item.getAttrGroupId();
        }).collect(Collectors.toList());

        List<AttrAttrgroupRelationEntity> attrAttrgroupRelationEntities = relationDao.selectList(new QueryWrapper<AttrAttrgroupRelationEntity>().in("attr_group_id", groupIds));
        //所有属性id
        List<Long> assoAttrId = attrAttrgroupRelationEntities.stream().map((item) -> {
            return item.getAttrId();
        }).collect(Collectors.toList());
        //移除 remove

        //获取本分组下但是不在已经关联的属性下

        QueryWrapper<AttrEntity> wrapper = new QueryWrapper<AttrEntity>().eq("catelog_id", catelogId).eq("attr_type", ProductConstant.AttrEnum.ATTR_TYPE_BASE.getCode());
        if(assoAttrId!=null&&assoAttrId.size()>0){
            wrapper.notIn("attr_id", assoAttrId);
        }
        String key= (String) params.get("key");
        if(StringUtils.isNotEmpty(key)){
            wrapper.and((w)->{
                w.eq("attr_id",key).or().like("attr_name",key);
            });
        }

        IPage<AttrEntity> page = this.page(new Query<AttrEntity>().getPage(params), wrapper);
        PageUtils pageUtils = new PageUtils(page);
        return pageUtils;
    }


}