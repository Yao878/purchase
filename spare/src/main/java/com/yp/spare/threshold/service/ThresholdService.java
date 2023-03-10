package com.yp.spare.threshold.service;


import com.yp.spare.threshold.bean.Threshold;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultVo;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/20 17:29
 版本号:1.0
 */
public interface ThresholdService {
    ResultVo thresholdQueryPage(QueryPageVo queryPageVo);

    ResultVo addThreshold(Threshold threshold);

    ResultVo updateThreshold(Threshold threshold);

    ResultVo updateStatus(Threshold threshold);
}
