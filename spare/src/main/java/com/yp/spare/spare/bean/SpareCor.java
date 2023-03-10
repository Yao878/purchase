package com.yp.spare.spare.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/14 10:21
 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class SpareCor {
    private Integer id;
    private Integer spareId;
    private List<Integer> legalPersonIds;
}
