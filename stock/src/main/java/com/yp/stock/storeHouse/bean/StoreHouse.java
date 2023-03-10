package com.yp.stock.storeHouse.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 库房表(Storehouse)实体类
 *
 * @author 唐山吴彦祖
 * @since 2023-03-08 00:31:02
 */
@Data
@Accessors(chain = true)
public class StoreHouse {
    /**
     * 库房id
     */
    private Integer id;
    /**
     * 库房编码
     */
    @NotBlank(message = "库房编码不能为空")
    private String code;
    /**
     * 库房名称
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
     * 状态名称
     */
    private String statusName;
    /**
     * 详细地址
     */

    private String address;
    /**
     * 标识外键
     */
    private String identCode;
    /**
     * 新建人
     */
    private String addMan;
    /**
     * 新增时间
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
    private Integer version;
    /**
     * 地区id
     */
    private String region;
    /**
     * 国家id
     */
    private String country;
    /**
     * 省份id
     */
    private String province;
    /**
     * 市区id
     */
    private String city;
    /**
     * 县区id
     */
    private String county;

}

