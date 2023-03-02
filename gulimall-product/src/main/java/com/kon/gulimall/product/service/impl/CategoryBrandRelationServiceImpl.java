package com.kon.gulimall.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.kon.gulimall.product.dao.BrandDao;
import com.kon.gulimall.product.dao.CategoryDao;
import com.kon.gulimall.product.entity.BrandEntity;
import com.kon.gulimall.product.entity.CategoryEntity;
import com.kon.gulimall.product.service.BrandService;
import com.kon.gulimall.product.service.CategoryService;
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

import com.kon.gulimall.product.dao.CategoryBrandRelationDao;
import com.kon.gulimall.product.entity.CategoryBrandRelationEntity;
import com.kon.gulimall.product.service.CategoryBrandRelationService;


@Service("categoryBrandRelationService")
public class CategoryBrandRelationServiceImpl extends ServiceImpl<CategoryBrandRelationDao, CategoryBrandRelationEntity> implements CategoryBrandRelationService {
    @Autowired
    BrandDao brandDao;
    @Autowired
    CategoryDao categoryDao;

    @Autowired
    CategoryBrandRelationDao relationDao;

    @Autowired
    BrandService brandService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryBrandRelationEntity> page = this.page(
                new Query<CategoryBrandRelationEntity>().getPage(params),
                new QueryWrapper<CategoryBrandRelationEntity>()
        );

        return new PageUtils(page);
    }



    @Override
    public void saveDetail(CategoryBrandRelationEntity categoryBrandRelation) {
        //根据品牌id获取到品牌name存入
        Long brandId=categoryBrandRelation.getBrandId();
        BrandEntity brandEntity=brandDao.selectById(brandId);
        categoryBrandRelation.setBrandName(brandEntity.getName());
        //根据分类id获取到分类name存入
        Long cateId=categoryBrandRelation.getCatelogId();
        CategoryEntity categoryEntity=categoryDao.selectById(cateId);
        categoryBrandRelation.setCatelogName(categoryEntity.getName());

        //保存
        this.save(categoryBrandRelation);

    }

    @Override
    public void updateBrand(Long brandId, String name) {
        //更新BrandRelation这个表中的name
        CategoryBrandRelationEntity relationEntity=new CategoryBrandRelationEntity();
        relationEntity.setBrandId(brandId);
        relationEntity.setBrandName(name);
        UpdateWrapper<CategoryBrandRelationEntity> wrapper=new UpdateWrapper<CategoryBrandRelationEntity>();
        wrapper.eq("brand_id",brandId);
        this.update(relationEntity,wrapper);


    }

    @Override
    public void updateCategory(Long catId, String name) {
        this.baseMapper.updateCategory(catId,name);
    }

    @Override
    public List<BrandEntity> getBrandsByCatId(Long catId) {
        //先查分类下的品牌
        List<CategoryBrandRelationEntity> catelogId = relationDao.selectList(new QueryWrapper<CategoryBrandRelationEntity>().eq("catelog_id", catId));
        List<BrandEntity> collect = catelogId.stream().map((item) -> {
            Long brandId = item.getBrandId();
            BrandEntity byId = brandService.getById(brandId);
            return byId;
        }).collect(Collectors.toList());
        //再查品牌具体信息并返回
        return collect;
    }

}