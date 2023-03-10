package com.yp.spare.spareCostPrice.bean;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/22 14:39
 * 版本号:1.0
 */
@Data
public class QueryPageVo {
    //计算得出的第一个参数
    private Integer offSize;
    //每页的条数
    private Integer pageSize;
    private Integer pageIndex;

    /*备件成本价*/
    private String legalPersonName;//法人姓名
    private String legalPersonId;//法人ID
    private String legalPersonCode;//法人编码
    private String legalPlantCode;//法人工厂编码
    private String taxType;//含税类型
    private String priceType;//价格类别
    private String currencyCode;//币种编码
    private String facCode;//工厂编码
    private String unitType;//计量单位类型
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate effectiveTime;//生效时间
    @DateTimeFormat(pattern = "yyyyMMdd")
    private LocalDate invalidTime;//失效时间
    private Date addTime;
    private String addUser;
    private Date updateTime;
    private String updateUser;
    private String spareCostPriceRemark;
    private String version;


}
