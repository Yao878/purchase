package com.yp.stock.storeHouse.bean;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)//链式写法
public class QueryPageVo {

    private Integer id;//主键编号

    private String name;//仓库名称
    private String code;//仓库编码
    private String remark;//备注
    private Integer status;//状态
    private String statusName;//状态名称
    private String address;//地址
    private String ident_code;//仓库标识码
    private Integer offSize;//计算得出的第一个参数
    private Integer pageSize;//每页的条数
    private Integer pageIndex;//当前页
}
