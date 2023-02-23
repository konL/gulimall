package com.kon.gulimall.product.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;

// equals和hashCode方法也涉及父类的属性
@EqualsAndHashCode(callSuper = true)
@Data
public class AttrResVo extends AttrVo {
    /**
     * 分类名称
     */
    private String catelogName;
    /**
     * 分组名称
     */
    private String groupName;
    /**
     * 分类路径
     */
    private Long[] catelogPath;

    private Integer valueType;
}
