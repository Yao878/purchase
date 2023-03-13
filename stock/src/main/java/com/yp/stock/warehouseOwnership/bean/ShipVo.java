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
public class ShipVo {
    private Integer id;
    private String name;//库房名称

    private String code;//库房编码

    private String remark;//库房描述

    private String storageSectionCode;//库区编码

    private String storageSectionName;//库区名称

    private String storageSectionRemark;//库区描述

    private Boolean status;//状态

    private String statusName;

    private String corName;//法人名称
    private String corCode;//法人名称
}
