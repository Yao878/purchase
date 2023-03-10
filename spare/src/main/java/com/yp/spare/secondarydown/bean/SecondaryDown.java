package com.yp.spare.secondarydown.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/28 14:44
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)
public class SecondaryDown {

    private Integer id;//主键id
    @NotBlank(message = "字典名称不能为空")
    private String dictionaryName;//字典名称
    private String parentCode;//父级编码
    @NotBlank(message = "字典编码不能为空")
    private String dictionaryCode;//字典编码
    private String remark;//备注
    @NotNull
    private Boolean status;//状态
    private List<SecondaryDown> secondarySon;//父类下拉框嵌套子类下拉框
}
