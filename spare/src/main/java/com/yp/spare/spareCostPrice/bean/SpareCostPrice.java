package com.yp.spare.spareCostPrice.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Date;

/*
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/21 11:22
 * 版本号:1.0
 * TODO:
 */
@Data
@Accessors(chain = true)//链式写法
public class SpareCostPrice {
    private Integer spareCostPriceId;//备件成本价id
    private String spareCostPrice;//备件单价
    private Integer spareId;//备件id
    private String spareCode;//备件编码
    private String spareName;//备件名称
    private Boolean status;//备件成本价状态
    private String statusName;//备件成本价状态名称
    private String priceType;//价格分类
    private String unitType;//计量单位类型
    private LocalDate effectiveTime;//生效时间
    private LocalDate invalidTime;//失效时间
    private String facCode;//工厂编码

    private String taxType;//含税类型
    private String currency;//币种

    private Integer currencyId;//币种id
    private Integer taxrateId;//税率id
    private Integer corId;//法人id
    private Date addTime;//添加时间
    private String addUser;//添加人
    private Date updateTime;//修改时间
    private String updateUser;//修改人
    private String spareCostPriceRemark;//备注
    private String version;//版本号
}
