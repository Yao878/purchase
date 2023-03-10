package com.yp.stock.utils;

import lombok.Data;

import java.util.Objects;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/7 16:54
 版本号:1.0
 */
@Data
public class ResultBuildVo<T> {
    public static ResultVo error(String errorMessage) {
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(500);
        resultVo.setErrorMessage(Objects.requireNonNull(errorMessage));
        return resultVo;
    }

    public static ResultVo success() {
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(200);
        return resultVo;
    }

    public static <T> ResultVo success(T data) {
        ResultVo resultVo = new ResultVo();
        resultVo.setSuccess(200);
        resultVo.setData(data);
        return resultVo;
    }
}
