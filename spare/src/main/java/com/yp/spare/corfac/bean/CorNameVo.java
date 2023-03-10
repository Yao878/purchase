package com.yp.spare.corfac.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/11 23:49
 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class CorNameVo {
    private Integer id;
    private String legalPersonName;
    private String legalPersonCode;
}
