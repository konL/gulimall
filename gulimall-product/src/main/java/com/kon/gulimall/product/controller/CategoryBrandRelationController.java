package com.kon.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kon.gulimall.product.entity.BrandEntity;
import com.kon.gulimall.product.vo.BrandVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kon.gulimall.product.entity.CategoryBrandRelationEntity;
import com.kon.gulimall.product.service.CategoryBrandRelationService;
import com.kon.common.utils.PageUtils;
import com.kon.common.utils.R;



/**
 * 品牌分类关联
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:17
 */
@RestController
@RequestMapping("product/categorybrandrelation")
public class CategoryBrandRelationController {
    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;


    /**
     * 获取分类与品牌关联关系
     * Controller只接收请求和校验数据，返回页面需要的response
     * service业务处理
     * @return
     */
    @GetMapping("/brands/list")
    public R relationBrandList(@RequestParam(value = "catId",required = true) Long catId){
        List<BrandEntity> vos=categoryBrandRelationService.getBrandsByCatId(catId);
        List<BrandVo> collect=vos.stream().map((item)->{
            BrandVo brandVo=new BrandVo();
            brandVo.setBrandId(item.getBrandId());
            brandVo.setBrandName(item.getName());
            return brandVo;
        }).collect(Collectors.toList());
        return R.ok().put("data",collect);


    }

    /**
     * 获取当前品牌分类列表（比如小米分类即是手机也有电视）
     */

    @GetMapping("/catelog/list")
    public R list(@RequestParam Long brandId){
       List<CategoryBrandRelationEntity> data=categoryBrandRelationService.list(
               new QueryWrapper<CategoryBrandRelationEntity>().eq("brand_id",brandId)
       );

        return R.ok().put("data", data);
    }

    /**
     *
     */

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = categoryBrandRelationService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		CategoryBrandRelationEntity categoryBrandRelation = categoryBrandRelationService.getById(id);

        return R.ok().put("categoryBrandRelation", categoryBrandRelation);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){

        //请求参数 "brandId":1,"catelogId":2}
		categoryBrandRelationService.saveDetail(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CategoryBrandRelationEntity categoryBrandRelation){
		categoryBrandRelationService.updateById(categoryBrandRelation);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		categoryBrandRelationService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
