package com.yp.stock.warehouseOwnership.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/9 20:16
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class SecendPageVo {
    //根据库房编码，库房名称，库区业务属性查询
    private String CorCode;
    private String WarehouseCode;//库房编码
    private String WarehouseName;//库房名称
    private List<Integer> areaBusinessAttribute;//库区业务
    private Integer offSize;//计算得出的第一个参数
    private Integer pageSize;//每页的条数
    private Integer pageIndex;//当前页
}
