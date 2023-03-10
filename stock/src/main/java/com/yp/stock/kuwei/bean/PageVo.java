package com.yp.stock.kuwei.bean;

import lombok.Data;

import java.util.List;

@Data

public class PageVo<E> {
    //当前页数
    private int pageIndex;
    //总条数
    private Integer totalCount;
    //每页存储条数
    private int pageSize;
    //总页数
    private int totalPage;

    //用户数据
    private List<E> dataList;


}
