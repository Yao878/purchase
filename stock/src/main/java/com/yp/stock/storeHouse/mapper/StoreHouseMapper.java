package com.yp.stock.storeHouse.mapper;

import com.yp.stock.storeHouse.bean.IdentQueryPageVo;
import com.yp.stock.storeHouse.bean.QueryPageVo;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.storeHouse.bean.StorehouseIdentification;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/7 19:59
 * 版本号:1.0
 */
@Mapper
public interface StoreHouseMapper {
    List<StoreHouse> queryPage(QueryPageVo queryPageVo);

    Integer add(StoreHouse storeHouse);

    Integer update(StoreHouse storeHouse);

    StoreHouse queryByName(String name);

    StoreHouse queryByCode(String code);

    Integer updateStatus(StoreHouse storeHouse);

    List<StorehouseIdentification> queryPageIdent(IdentQueryPageVo queryPageVo);

    Integer addIdent(StorehouseIdentification storehouseIdentification);

    StorehouseIdentification queryIdentByName(String name);

    Integer updateIdent(StorehouseIdentification storehouseIdentification);

    Integer updateStatusIdent(StorehouseIdentification storehouseIdentification);

    StorehouseIdentification queryIdentById(Integer id);

    StoreHouse queryById(Integer id);

    List<StoreHouse> getDown();

    List<StorehouseIdentification> getIdent();

    List<String> queryArea();

    List<String> queryCountry();

    List<String> queryProvince();

    List<String> queryCity();

    List<String> queryCounty();
}
