package com.yp.spare.spareCostPrice.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/6 16:25
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class importSpare {
    private String CorName;
    private String PriceType;
    private String effectiveTime;
    private String invalidTime;
}
