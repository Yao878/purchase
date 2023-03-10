package com.yp.spare.spare.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/14 10:21
 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class Spare {
    private Integer id;
    //备件编码
    private String code;
    private String name;
    private Boolean status;
    private String statusName;
    private List<Integer> legalPersonIds;
    private List<Integer> legalPersonId;
    //法人名称
    private List<String> legalPersonNames;
    private String legalPersonName;
    private Date addtime;
    private String addman;
    private Date updatetime;
    private String updateman;
    private Integer version;
    //小类
    private Integer subClassId;
    private String subClassName;
    //大类
    private Integer planClassId;
    private String planClassName;

}
