package com.yp.stock.warehouseOwnership.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author huimengYuan
 * @date 2023/2/7
 * 对照表的全字段
 */
@Data
@Accessors(chain = true)//链式写法
public class QueryPageVo {

    /**
     * 库房编码
     */
    private String WarehouseCode;
    /**
     * 库房名称
     */
    private String WarehouseName;
    /**
     * 库房描述
     */
    private String WarehouseRemark;
    /**
     * 法人名称
     */
    private String CorName;
    /**
     * 法人编码
     */
    private String CorCode;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 状态名称
     */
    private String StatusName;
    /**
     * 库区业务属性
     */
    private String areaAttribute;
    private Integer offSize;//计算得出的第一个参数
    private Integer pageSize;//每页的条数
    private Integer pageIndex;//当前页
}
