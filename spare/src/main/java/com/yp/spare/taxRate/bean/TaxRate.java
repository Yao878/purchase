package com.yp.spare.taxRate.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/21 15:07
 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class TaxRate {
    private Integer taxRateId;//税率id
    private String taxCode;//税率编码
    private String taxDec;//税率描述
    private Integer tax;//税率值
    private Boolean status;//税率状态
    private String statusName;//税率状态名称
    private Date taxRateAddTime;//添加时间
    private String taxRateAddUser;//添加人
    private Date taxRateUpdateTime;//修改时间
    private String taxRateUpdateUser;//修改人
    private String version;//版本号

}
