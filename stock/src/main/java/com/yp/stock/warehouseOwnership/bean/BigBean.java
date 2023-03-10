package com.yp.stock.warehouseOwnership.bean;

import com.yp.stock.area.bean.Area;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.storeHouse.bean.StoreHouse;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/9 20:17
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)//链式写法
public class BigBean {
    //大实体类里面放三个集合，每个集合里放库房对象，库区对象，库位对象

    private List<Area> areaList;//库区集合
    private List<StoreHouse> storeHouseList;//库房集合
    private List<Kuwei> kuweiList;//库位集合
}
