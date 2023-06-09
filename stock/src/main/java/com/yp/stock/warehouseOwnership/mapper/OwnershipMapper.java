package com.yp.stock.warehouseOwnership.mapper;

import com.yp.stock.area.bean.Area;
import com.yp.stock.area.bean.AreaAttribute;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.warehouseOwnership.bean.BigBean;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.ShipVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface OwnershipMapper {

    List<ShipVo> queryFirstPage(ShipVo shipVo);

    int addOwnership(Ownership ownership);
    List<Area> queryBusiness(String areaCode, String businessCode);

    List<Kuwei> selectKuweiByLegalPersonId(String legalPersonCode);

    List<Area> selectAreaByKuweiList(List<Kuwei> kuweiList);

    List<StoreHouse> selectStoreHouseByAreaList(List<Area> areaList);

    List<StoreHouse> selectStoreHouse(BigBean bigBean);

    List<Area> queryAreaList(@Param("storeHouseList") List<StoreHouse> storeHouseList, @Param("attributeList") List<AreaAttribute> AreaAttribute);

    List<Area> selectAreaByStoreHouseList(List<StoreHouse> storeHouseList);

    List<Kuwei> selectKuwei(List<Area> areaList);

    Integer updateCorWarehouse(List<Kuwei> kuweiList);

    int updateStatus(Ownership ownership);
}
