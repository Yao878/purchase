package com.yp.spare.spare.service;

import com.yp.spare.spare.bean.Spare;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultVo;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/14 10:20
 版本号:1.0
 */
public interface SpareService {
    ResultVo spareQueryPage(QueryPageVo queryPageVo);

    ResultVo updateStatus(Spare spare);

    ResultVo addSpare(Spare spare);

    ResultVo updateSpare(Spare spare);


}
