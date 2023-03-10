package com.yp.stock.qianduan;

import lombok.Data;

@Data
public class ResultVO<T> {
    private boolean success;
    private String errorCode;
    private String errorMessage;
    private T data;//正常数据
//    private Object hint;//异常数据
//    private Object[] errorArgs;

}
