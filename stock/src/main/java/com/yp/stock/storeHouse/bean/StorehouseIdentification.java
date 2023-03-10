package com.yp.stock.storeHouse.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 库房标识(StorehouseIdentification)实体类
 *
 * @author 唐山吴彦祖
 * @since 2023-03-08 09:03:22
 */
@Data
@Accessors(chain = true)
public class StorehouseIdentification {
    /**
     * 库房标识编号
     */
    private Integer id;
    /**
     * 标识编码
     */
    @NotBlank(message = "标识编码不能为空")
    private String code;
    /**
     * 标示名称
     */
    @NotBlank(message = "标示名称不能为空")
    private String name;
    /**
     * 标识描述
     */
    private String remark;
    /**
     * 状态 1：启用 0：禁用
     */
    private Boolean status;
    /**
     * 状态名称
     **/
    private String statusName;
    /**
     * 新增人
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
    /**
     * 版本号
     */
    private Integer version;

}

