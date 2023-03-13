package com.yp.stock.warehouseOwnership.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

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
    private String storehouseCode;
    /**
     * 库房名称
     */
    private String storehouseName;
    /**
     * 库房描述
     */
    private String storehouseRemark;
    /**
     * 法人名称
     */
    private String CorName;
    /**
     * 法人编码
     */
    private String corCode;
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
    private List<Integer> areaAttribute;
    private Integer offSize;//计算得出的第一个参数
    private Integer pageSize;//每页的条数
    private Integer pageIndex;//当前页
}
