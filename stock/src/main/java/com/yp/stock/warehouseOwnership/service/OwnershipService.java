package com.yp.stock.warehouseOwnership.service;

import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.QueryPageVo;
import com.yp.stock.warehouseOwnership.bean.SecendPageVo;

public interface OwnershipService {

    ResultVo queryFirstPage(QueryPageVo queryPageVo);

    ResultVo querySecondPage(SecendPageVo secendPageVo);

    ResultVo updateSituation(Ownership ownership);

    ResultVo addOwnership(Ownership ownership);

    ResultVo querySecondPageByStorehouse(SecendPageVo secendPageVo);
}
