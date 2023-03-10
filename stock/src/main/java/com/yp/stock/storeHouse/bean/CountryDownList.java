package com.yp.stock.storeHouse.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/9 13:07
 * 版本号:1.0
 */
@Data
@Accessors(chain = true)
public class CountryDownList {
    //地区集合
    private List<String> regionList;
    //国家集合
    private List<String> countryList;
    //省份集合
    private List<String> provinceList;
    //城市集合
    private List<String> cityList;
    //区县集合
    private List<String> countyList;
}
