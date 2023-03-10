package com.yp.spare.spLittleCategory.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/*
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/21 20:52
 * 版本号:1.0
 */
//备件计划小类明细
@Data
@AllArgsConstructor//全参构造
@NoArgsConstructor//无参构造
@Accessors(chain = true)//链式写法
public class SpLittleCategory {
    private String xuhao;
    private Integer id;
    @NotBlank(message = "备件小类名称不能为空")
    private String name;
    private Boolean status;
    @NotNull(message = "备件小类状态不能为空")
    private String statusName;
    @NotBlank(message = "备件小类编码不能为空")
    private String code;
    private String remark;
    private Date categoryAddTime;
    private String categoryAddMan;
    private Date categoryUpdateTime;
    private String categoryUpdateMan;
    private Integer version;

}
