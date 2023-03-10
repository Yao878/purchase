package com.yp.stock.qianduan;

import lombok.extern.java.Log;
/*import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.TransactionSynchronizationManager;*/

@Log
public class ResultVOBuilder {

    public static <T> ResultVO<T> success(T t) {
        ResultVO<T> result = new ResultVO<>();
        result.setData(t);
        result.setSuccess(true);
        return result;
    }

    public static ResultVO error(String errorCode, String message) {
        ResultVO result = new ResultVO();
        result.setSuccess(false);
        result.setErrorCode(errorCode);
        result.setErrorMessage(message);
        return result;
    }
}
