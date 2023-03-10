package com.yp.stock.area.bean;

import lombok.Data;
import lombok.experimental.Accessors;


@Data
@Accessors(chain = true)//链式写法
public class AreaAttributeQueryPageVo {

    //计算得出的第一个参数
    private Integer offSize;
    //每页的条数
    private Integer pageSize;
    private Integer pageIndex;

    //库区属性
    /**
     * 库区属性编码
     */
    private String code;
    /**
     * 库区属性名字
     */

    private String name;
    /**
     * 库区属性描述
     */

    private String remark;
    /**
     * 库区属性状态
     */

    private Integer status;


}
