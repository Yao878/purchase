package com.yp.spare.spBroadCategory.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/*
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/21 20:49
 * 版本号:1.0
 */
//备件计划大类明细
@ConfigurationProperties(value = "spbroadcategory")
@Component
@Data
@Accessors(chain = true)//链式写法
public class SpBroadCategory {
    private Integer id;
    @NotBlank(message = "备件大类名称不能为空")
    private String name;
    private String remark;
    @NotNull(message = "备件大类状态不能为空")
    private Boolean status;
    private String statusName;
    @NotBlank(message = "备件大类编码不能为空")
    private String code;
    private Date categoryAddTime;
    private String categoryAddMan;
    private Date categoryUpdateTime;
    private String categoryUpdateMan;
    private Integer version;
}
