package com.yp.stock.warehouseOwnership.service;

import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.BigBean;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.ShipVo;

public interface OwnershipService {

    ResultVo queryFirstPage(ShipVo shipVo);

    ResultVo addOwnership(Ownership ownership);

    ResultVo querySecondPageByStorehouse(BigBean bigBean);

    ResultVo updateStatus(Ownership ownership);

    ResultVo queryBusiness(String areaCode, String businessCode);

    ResultVo update(BigBean bigBean);

}
