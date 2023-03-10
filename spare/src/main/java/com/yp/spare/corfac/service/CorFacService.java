package com.yp.spare.corfac.service;

import com.yp.spare.corfac.bean.CorFac;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultVo;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/10 20:32
 版本号:1.0
 */
public interface CorFacService {
    ResultVo getCorFacByPage(QueryPageVo queryPageVo);

    ResultVo addCorFac(CorFac corFac);

    ResultVo updateCorFac(CorFac corFac);

    ResultVo updateStatus(CorFac corFac);
}
