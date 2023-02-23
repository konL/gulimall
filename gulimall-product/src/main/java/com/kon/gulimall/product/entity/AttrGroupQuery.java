package com.kon.gulimall.product.entity;


import lombok.Data;

@Data
public class AttrGroupQuery {
//    page: 1,//当前页码
    private Integer page;
//    limit: 10,//每页记录数
    private Integer limit;
//    sidx: 'id',//排序字段
    private String id;
//    order: 'asc/desc',//排序方式
    private String order;
//    key: '华为'//检索关键字
    private String key;
}
