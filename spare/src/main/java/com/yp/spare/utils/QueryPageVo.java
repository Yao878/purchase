package com.yp.spare.utils;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)//链式写法
public class QueryPageVo {
    /*法人工厂*/
    private String code;
    private String name;
    private Boolean status;
    //工厂编码
    private String legalPlantCode;
    //工厂描述
    private String legalPlantDec;
    //工厂名称
    private String legalPlantName;
    private Boolean enabled;
    private String remark;
    //法人名称
    private String legalPersonName;
    //法人编码
    private String legalPersonCode;
    //计算得出的第一个参数
    private Integer offSize;
    //每页的条数
    private Integer pageSize;
    private Integer pageIndex;
    /*阈值*/
    private String spareName;
    private String spareCode;
    private String subClassName;
    private String updateman;
    private String maxNum;
    private String minNum;
    private String updateTimeStart;
    private String updateTimeEnd;

    /*税码税率*/
    private String taxCode;
    private String taxDec;
    private Integer tax;

}
