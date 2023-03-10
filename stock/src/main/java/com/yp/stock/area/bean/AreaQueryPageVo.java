package com.yp.stock.area.bean;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)//链式写法
public class AreaQueryPageVo {

    //计算得出的第一个参数
    private Integer offSize;
    //每页的条数
    private Integer pageSize;
    private Integer pageIndex;
    /**
     * 库区编码
     */
    private String areaCode;
    /**
     * 库区名字
     */

    private String areaName;
    /**
     * 库区描述
     */

    private String remark;

    /**
     * 库区名字
     */

    private String areaStatus;
    /**
     * 库区状态
     */

    private Integer status;
    /**
     * 库房编码
     */
    private String wareHouseCode;
    /**
     * 库房名字
     */

    private String wareHouseName;


}
