package com.yp.stock.storeHouse.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author huimengYuan
 * @date 2023/2/7
 * 对照表的全字段
 */
@Data
@Accessors(chain = true)//链式写法
public class IdentQueryPageVo {

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
    private Integer status;
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
    private Integer offSize;//计算得出的第一个参数
    private Integer pageSize;//每页的条数
    private Integer pageIndex;//当前页
}
