package com.kon.gulimall.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.kon.common.group.AddGroup;
import com.kon.common.group.ListValue;
import com.kon.common.group.UpdateGroup;
import com.kon.common.group.UpdateStatusGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.*;

/**
 * 品牌
 * 
 * @author kon
 * @email kon@gmail.com
 * @date 2023-02-07 00:04:17
 */
@Data
@TableName("pms_brand")
public class BrandEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 品牌id
	 */
	@NotNull(message = "修改时，品牌id不能为空", groups = {UpdateGroup.class})
	@Null(message = "新增时，品牌id不需要提供", groups = {AddGroup.class})
	@TableId
	private Long brandId;
	/**
	 * 品牌名
	 *
	 * @NotBlank: 该注解的元素不能为null而且需要包含至少一个非空格的字符。
	 */
	@NotBlank(message = "品牌名不能为空")
	private String name;
	/**
	 * 品牌logo地址
	 */
	@NotEmpty(message = "logo不能为空")
	@URL(message = "logo必须是一个合法的URL地址")
	private String logo;
	/**
	 * 介绍
	 */
	private String descript;
	/**
	 * 显示状态[0-不显示；1-显示]
	 */
	@NotNull(message = "显示状态不能为空", groups = {AddGroup.class, UpdateStatusGroup.class})
	@ListValue(vals = {0, 1}, groups = {AddGroup.class, UpdateStatusGroup.class})
	private Integer showStatus;
	/**
	 * 检索首字母
	 */
	@NotEmpty(message = "首字母不能为空")
	@Pattern(regexp = "^[a-zA-Z]$", message = "首字母只能是一个字母")
	private String firstLetter;
	/**
	 * 排序
	 */
	@NotNull(message = "排序不能为空")
	@Min(value = 0, message = "排序必须大于等于0")
	private Integer sort;
}
