package com.yp.stock.utils;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)//链式写法
public class AllRecordsVo<T> {
    private List<T> dataList;//返回的数据集合
    private Integer currentPage;//当前页
    private Integer totalSize;//总条数
    private Integer totalPage;//总页数
    private Integer pageSize;//每页的条数


}
