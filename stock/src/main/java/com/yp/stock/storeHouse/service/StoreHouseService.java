package com.yp.stock.storeHouse.service;

import com.yp.stock.storeHouse.bean.IdentQueryPageVo;
import com.yp.stock.storeHouse.bean.QueryPageVo;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.storeHouse.bean.StorehouseIdentification;
import com.yp.stock.utils.ResultVo;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/7 19:32
 * 版本号:1.0
 */

public interface StoreHouseService {
    ResultVo queryPage(QueryPageVo queryPageVo);

    ResultVo add(StoreHouse storeHouse);

    ResultVo update(StoreHouse storeHouse);

    ResultVo updateStatus(StoreHouse storeHouse);

    ResultVo queryPageIdent(IdentQueryPageVo queryPageVo);

    ResultVo addIdent(StorehouseIdentification storehouseIdentification);

    ResultVo updateIdent(StorehouseIdentification storehouseIdentification);

    ResultVo updateStatusIdent(StorehouseIdentification storehouseIdentification);

    ResultVo queryArea();
}
