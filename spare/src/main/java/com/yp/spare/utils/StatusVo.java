package com.yp.spare.utils;/*
 * @Author: huimengYuan
 * @params: StatusVo
 */

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author huimengYuan
 * @date 2023/2/9
 */
@Data
@Accessors(chain = true)//链式写法
public class StatusVo {

    //状态值
    private String label;

    //状态码
    private Integer value;

    public StatusVo(String label, Integer value) {
        this.label = label;
        this.value = value;
    }
}
