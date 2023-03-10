package com.yp.stock.area.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 库区表(ReservoirArea)实体类
 *
 * @author 唐山吴彦祖
 * @since 2023-03-08 09:03:22
 */
@Data
@Accessors(chain = true)
public class Area {
    /**
     * 库区id
     */
    private Integer id;
    /**
     * 库区编码
     */
    private String areaCode;
    /**
     * 库位查询参数
     */
    private String code;
    /**
     * 描述
     */
    private String remark;
    /**
     * 名称
     */

    private String name;
    /**
     * 库房编码
     */

    private String wareHouseCode;
    /**
     * 库房名字
     */

    private String wareHouseName;
    /**
     * 库房描述
     */

    private String wareHouseRemark;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 状态名称
     */
    private String statusName;
    /**
     * 库位管理
     */

    private Integer management;
    /**
     * 集成
     */

    private Integer wms;
    /**
     * VIp
     */

    private Integer vip;
    /**
     * 属性列表
     */
    private List<String> attributeList;
    /**
     * 新建人
     */
    private Date addTime;
    /**
     * 新建人
     */
    private String addMan;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 修改人
     */
    private String updateMan;
    /**
     * 版本
     */
    private Integer version;

}

