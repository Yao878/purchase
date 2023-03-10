package com.yp.spare.utils;

import lombok.Data;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/7 16:31
 版本号:1.0
 */
@Data
public class ResultVo<T> {
    //是否成功,true为成功,false为失败
    private Integer Success;
    //提示信息
    private String errorMessage;
    //返回的数据
    private T data;
}
