package com.yp.stock.area.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 库区业务(BusinessAttribute)实体类
 *
 * @author 唐山吴彦祖
 * @since 2023-03-08 09:03:22
 */
@Data
@Accessors(chain = true)
public class AreaAttribute {
    /**
     * 业务id
     */
    private Integer id;
    /**
     * 编码
     */
    private String code;
    /**
     * 名称
     */

    private String name;
    /**
     * 描述
     */
    private String remark;
    /**
     * 状态
     */

    private Boolean status;
    /**
     * 状态
     */
    private String statusName;
    /**
     * 添加人
     */
    private String addMan;
    /**
     * 添加时间
     */
    private Date addTime;
    /**
     * 修改人
     */
    private String updateMan;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 版本
     */
    private Integer version;

}

