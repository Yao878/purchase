package com.yp.stock.warehouseOwnership.mapper;

import com.yp.stock.area.bean.Area;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.warehouseOwnership.bean.FirstPageVo;
import com.yp.stock.warehouseOwnership.bean.QueryPageVo;
import com.yp.stock.warehouseOwnership.bean.SecendPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OwnershipMapper {

    List<String> updateSituationByStoreCode(List<String> storeCodeList);

    List<String> updateSituationByAreaCode(List<String> areaCodeList);

    List<String> updateStoreSituationByCorCode(String corCode);

    List<String> updateAreaSituationByCorCode(String corCode);

    List<String> updateWareSituationByCorCode(String corNCode);

    void deleteAllOwnershipByCorCode(String corCode);

    void addOwnership(@Param("corCode") String corCode, @Param("warehouseCodeList") List<String> warehouseCodeList);


    List<FirstPageVo> queryFirstPage(QueryPageVo queryPageVo);

    Integer queryFirstPageCount(QueryPageVo queryPageVo);

    List<StoreHouse> queryStoreList(SecendPageVo secendPageVo);

    List<Area> queryAreaList(SecendPageVo secendPageVo);

    List<Kuwei> queryKuweiList(SecendPageVo secendPageVo);

    List<Kuwei> queryKuweiListByCorCode(String corCode);

    String queryCorCode(String corName);

    List<Area> queryAreaListByKuweiList(List<Kuwei> kuweiList);

    List<StoreHouse> queryStoreListByAreaList(List<Area> areaList);

    List<StoreHouse> queryStoreListByStorehouse(SecendPageVo secendPageVo);

    List<Area> queryAreaListByStorehouseList(List<StoreHouse> storeHouseList);

    List<Kuwei> queryKuweiListByAreaList(List<Area> areaList);
}
