package com.yp.spare.threshold.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/20 16:11
 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class Threshold {
    //阈值id
    private Integer id;
    //备件ID
    private Integer spareId;
    //备件名称
    private String spareName;
    //备件编号
    private String spareCode;
    //计量单位
    private String measurement;
    //备件小类id
    private Integer subClassId;
    //备件小类名称
    private String subClassName;
    //备件数量阈值
    private Integer spareNum;
    //最大阈值
    private Integer maxNum = 999;
    //最小阈值
    private Integer minNum = 0;
    //阈值状态(0:禁用,1:启用)
    private Boolean status;
    //阈值状态名称
    private String statusName;
    //是否可销售(0:不可销售,1:可销售)
    private Boolean isSale;
    //是否可销售的名称
    private String isSaleName;
    //是否可借用(0:不可借用,1:可借用)
    private Boolean isBorrow;
    //是否可借用的名称
    private String isBorrowName;
    //备注
    private String remark;
    //法人名称
    private String legalPersonName;
    //创建时间
    private Date addtime;
    //创建人
    private String addman;
    //开始修改时间
    private Date updateTimeStart;
    //结束修改时间
    private Date updateTimeEnd;
    //最后修改时间
    private Date updatetime;
    //最后修改人
    private String updateman;
    //版本号默认为1
    private Integer version;
}
