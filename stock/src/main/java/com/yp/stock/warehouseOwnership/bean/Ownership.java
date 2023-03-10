package com.yp.stock.warehouseOwnership.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/8 11:36
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)
public class Ownership {
    /**
     * 库房编码
     */
    private List<String> storeCode;
    /**
     * 库区编码
     */
    private List<String> areaCode;
    /**
     * 库位编码
     */
    private List<String> warehouseCode;
    /**
     * 法人编码
     */
    private String CorCode;

}
