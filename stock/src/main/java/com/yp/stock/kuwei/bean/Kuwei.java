package com.yp.stock.kuwei.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * 库位表(Warehouse)实体类
 *
 * @author 唐山吴彦祖
 * @since 2023-03-08 09:03:22
 */
@Data
@Accessors(chain = true)
public class Kuwei {
    /**
     * excel序号
     */
    private String xuhao;
    /**
     * 库位id
     */
    private Integer id;
    /**
     * 库位编码
     */
    @NotBlank(message = "code不为空")
    private String code;
    /**
     * 名称
     */
    @NotBlank(message = "name不为空")
    private String name;
    /**
     * 状态
     */
    private Boolean status;
    /**
     * 状态name
     */
    private String statusName;
    /**
     * 库区编码
     */
    private String storageSectionCode;
    /**
     * 库区名称
     */
    private String storageSectionName;
    /**
     * 库区描述
     */
    private String storageSectionRemark;

    /**
     * 库房编码
     */
    private String wareHouseCode;
    /**
     * 库房名称
     */
    private String wareHouseName;
    /**
     * 库房描述
     */
    private String wareHouseRemark;
    /**
     * 法人编码
     */
    private String corCode;
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
     * 版本号
     */
    private Integer version;

}

