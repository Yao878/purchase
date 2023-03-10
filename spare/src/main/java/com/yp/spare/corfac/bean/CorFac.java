package com.yp.spare.corfac.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import java.util.Date;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/10 16:17
 版本号:1.0
 */
@Component
@Data
@Accessors(chain = true)//链式写法
public class CorFac {

    private Integer id;
    //工厂编码

    private String legalPlantCode;
    //工厂描述
    private String legalPlantDec;
    private Boolean enabled;
    private String statusName;
    //法人编码
    private String legalPersonCode;
    //法人名称
    private String legalPersonName;
    private String facAddMan;
    private Date facAddTime;
    private String facUpdateMan;
    private Date facUpdateTime;
    private Integer facVersion;
}
