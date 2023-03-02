package com.kon.gulimall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kon.gulimall.product.entity.AttrEntity;
import com.kon.gulimall.product.entity.AttrGroupQuery;
import com.kon.gulimall.product.service.AttrAttrgroupRelationService;
import com.kon.gulimall.product.service.AttrService;
import com.kon.gulimall.product.service.CategoryService;
import com.kon.gulimall.product.vo.AttrGroupRelationVo;
import com.kon.gulimall.product.vo.AttrGroupWithAttrsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.kon.gulimall.product.entity.AttrGroupEntity;
import com.kon.gulimall.product.service.AttrGroupService;
import com.kon.common.utils.PageUtils;
import com.kon.common.utils.R;



/**
 * 属性分组
 *
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:17
 */
@RestController
@RequestMapping("product/attrgroup")
public class AttrGroupController {
    @Autowired
    private AttrGroupService attrGroupService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AttrService attrService;

    /**
     * 查询分组关联属性
     */
    @GetMapping("/{attrgroupId}/attr/relation")
    public R attrRelation(@PathVariable("attrgroupId") Long attrgroupId) {
        List<AttrEntity> list = attrService.getRelationAttr(attrgroupId);

        return R.ok().put("data", list);
    }

    //http://localhost:88/api/product/attrgroup/2/noattr/relation?t=1661152895484&page=1&limit=10&key=
    /**
     * 查询当前分类下未被关联的属性
     */
    @GetMapping("/{attrgroupId}/noattr/relation")
    public R attrNoRelation(@PathVariable("attrgroupId") Long attrgroupId,
                            @RequestParam Map<String, Object> params){
        PageUtils page = attrService.getNoRelationAttr(attrgroupId,params);
        return R.ok().put("page",page);
    }
    /**
     * 列表
     */
    @RequestMapping("/list/{catelogId}")
    public R list(@RequestParam Map<String, Object> params, @PathVariable("catelogId") Long catelogId) {

//        PageUtils page = attrGroupService.queryPage(params);
        PageUtils page = attrGroupService.queryPage(params,catelogId);
        return R.ok().put("page", page);
    }
//http://localhost:88/api/product/attrgroup/attr/relation/delete
    /**
     * 删除关联
     */
    @PostMapping("/attr/relation/delete")
    public R deleteRelation(@RequestBody AttrGroupRelationVo[] vos) {
        attrService.deleteRelation(vos);

        return R.ok();
    }

    @Autowired
    AttrAttrgroupRelationService attrAttrgroupRelationService;
    /**
     * 新增属性与分组的关联关系
     */
    @PostMapping("/attr/relation")
    public R addRelation(@RequestBody List<AttrGroupRelationVo> attrGroupRelationVoList) {
        attrAttrgroupRelationService.saveRelationBatch(attrGroupRelationVoList);

        return R.ok();
    }

    ///product/attrgroup/{catelogId}/withattr
    @GetMapping("/{catelogId}/withattr")
    public R getAttrGroupWithAttrs(@PathVariable("catelogId")Long catelogId){

        //1、查出当前分类下的所有属性分组，
        //2、查出每个属性分组的所有属性
        List<AttrGroupWithAttrsVo> vos =  attrGroupService.getAttrGroupWithAttrsByCatelogId(catelogId);
        return R.ok().put("data",vos);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{attrGroupId}")
    public R info(@PathVariable("attrGroupId") Long attrGroupId){
		AttrGroupEntity attrGroup = attrGroupService.getById(attrGroupId);
		//根据id获取完整路径
        Long catelogId=attrGroup.getCatelogId();
        Long[] path=categoryService.findCatelogPath(catelogId);
        attrGroup.setCatelogPath(path);


        return R.ok().put("attrGroup", attrGroup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.save(attrGroup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody AttrGroupEntity attrGroup){
		attrGroupService.updateById(attrGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] attrGroupIds){
		attrGroupService.removeByIds(Arrays.asList(attrGroupIds));

        return R.ok();
    }

}
