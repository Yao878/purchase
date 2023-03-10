package com.yp.stock.kuwei.bean;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 库位查询bean
 */
@Data
@Accessors(chain = true)//链式写法
public class KuweiQueryPageVo {

    /**
     * 库位编码
     */
    private String code;
    /**
     * 库位名称
     */
    private String name;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 库区编码
     */
    private String storageSectionCode;
    /**
     * 库区名称
     */
    private String storageSectionName;

    /**
     * 库房编码
     */
    private String wareHouseCode;
    /**
     * 库房名称
     */
    private String wareHouseName;


    /**
     * 当前显示条数
     */
    private Integer pageSize;

    /**
     * 前端查询分页传参：当前页码
     */
    private Integer pageIndex;
    /**
     * 前端查询分页第一参数
     */
    private Integer offSize;

}
