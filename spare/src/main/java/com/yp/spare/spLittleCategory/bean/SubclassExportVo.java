package com.yp.spare.spLittleCategory.bean;

import com.yp.spare.utils.QueryPageVo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/3 20:38
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class SubclassExportVo extends QueryPageVo {
    private String code;
    private String name;
    private Boolean status;
}
