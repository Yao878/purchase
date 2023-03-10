package com.yp.stock.storeHouse.serviceImpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yp.stock.storeHouse.bean.*;
import com.yp.stock.storeHouse.mapper.StoreHouseMapper;
import com.yp.stock.storeHouse.service.StoreHouseService;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/7 19:32
 * 版本号:1.0
 */
@Service
public class StoreHouseServiceImpl implements StoreHouseService {
    @Autowired
    private StoreHouseMapper storeHouseMapper;

    @Override
    public ResultVo queryPage(QueryPageVo queryPageVo) {
        PageHelper.startPage(queryPageVo.getPageIndex(), queryPageVo.getPageSize());
        PageInfo<StoreHouse> of = PageInfo.of(storeHouseMapper.queryPage(queryPageVo));
        List<StoreHouse> kuFangVoList = storeHouseMapper.queryPage(queryPageVo);
        for (StoreHouse Ident : kuFangVoList) {
            Ident.setStatusName(Ident.getStatus() ? "启用" : "禁用");
        }
        of.setList(kuFangVoList);
        return ResultBuildVo.success(of);
    }

    @Override
    public ResultVo add(StoreHouse storeHouse) {
        //数据校验 判断编码，名称，详细地址是否为空
        if (StringUtils.isEmpty(storeHouse.getCode())) {
            return ResultBuildVo.error("库房编码不能为空");
        }
        if (StringUtils.isEmpty(storeHouse.getName())) {
            return ResultBuildVo.error("库房名称不能为空");
        }
        if (StringUtils.isEmpty(storeHouse.getAddress())) {
            return ResultBuildVo.error("库房详细地址不能为空");
        }
        if (storeHouse.getIdentCode() == null) {
            return ResultBuildVo.error("库房标识不能为空");
        }
        //判断库房编码，名称是否重复
        StoreHouse nameHouse = storeHouseMapper.queryByName(storeHouse.getName());
        if (nameHouse != null) {
            return ResultBuildVo.error("库房名称已存在");
        }
        StoreHouse codeHouse = storeHouseMapper.queryByCode(storeHouse.getCode());
        if (codeHouse != null) {
            return ResultBuildVo.error("库房编码已存在");
        }
        storeHouse.setAddMan("admin").setUpdateMan("admin");
        return ResultBuildVo.success(storeHouseMapper.add(storeHouse));
    }

    @Override
    public ResultVo update(StoreHouse storeHouse) {
        //根据前端传过来的id查询出库房信息
        StoreHouse storeHouse1 = storeHouseMapper.queryById(storeHouse.getId());
        if (storeHouse1.getStatus()) {
            return ResultBuildVo.error("库房已启用，不能修改");
        }
        //数据校验 判断名称，详细地址是否为空
        if (StringUtils.isEmpty(storeHouse.getName())) {
            return ResultBuildVo.error("库房名称不能为空");
        }
        if (StringUtils.isEmpty(storeHouse.getAddress())) {
            return ResultBuildVo.error("库房详细地址不能为空");
        }
        //查询数据库中是否存在该名称
        StoreHouse oldHouse = storeHouseMapper.queryByName(storeHouse.getName());
        if (oldHouse != null && !oldHouse.getId().equals(storeHouse.getId())) {
            return ResultBuildVo.error("库房名称已存在");
        } else {
            storeHouse.setUpdateMan("admin");
            return ResultBuildVo.success(storeHouseMapper.update(storeHouse));
        }
    }

    @Override
    public ResultVo updateStatus(StoreHouse storeHouse) {
        //数据校验 判断状态是否为空
        if (storeHouse.getStatus() == null) {
            return ResultBuildVo.error("库房状态不能为空");
        }
        storeHouse.setUpdateMan("admin");
        Integer integer = storeHouseMapper.updateStatus(storeHouse);
        if (integer > 0) {
            return ResultBuildVo.success("修改成功");
        } else {
            return ResultBuildVo.error("修改失败");
        }
    }

    @Override
    public ResultVo queryPageIdent(IdentQueryPageVo queryPageVo) {
        PageHelper.startPage(queryPageVo.getPageIndex(), queryPageVo.getPageSize());
        PageInfo<StorehouseIdentification> of = PageInfo.of(storeHouseMapper.queryPageIdent(queryPageVo));
        //查询出的数据
        List<StorehouseIdentification> kuFangVoList = storeHouseMapper.queryPageIdent(queryPageVo);
        for (StorehouseIdentification Ident : kuFangVoList) {
            if (Ident.getStatus()) {
                Ident.setStatusName("启用");
            } else {
                Ident.setStatusName("禁用");
            }
        }
        of.setList(kuFangVoList);
        return ResultBuildVo.success(of);
    }

    @Override
    public ResultVo addIdent(StorehouseIdentification storehouseIdentification) {
        //数据校验 判断编码，名称是否为空
        if (StringUtils.isEmpty(storehouseIdentification.getCode())) {
            return ResultBuildVo.error("库房标识编码不能为空");
        }
        if (StringUtils.isEmpty(storehouseIdentification.getName())) {
            return ResultBuildVo.error("库房标识名称不能为空");
        }
        //判断库房编码，名称是否重复
        StoreHouse nameHouse = storeHouseMapper.queryByName(storehouseIdentification.getName());
        if (nameHouse != null) {
            return ResultBuildVo.error("库房标识名称已存在");
        }
        StoreHouse codeHouse = storeHouseMapper.queryByCode(storehouseIdentification.getCode());
        if (codeHouse != null) {
            return ResultBuildVo.error("库房标识编码已存在");
        }
        storehouseIdentification.setAddMan("admin").setUpdateMan("admin");
        return ResultBuildVo.success(storeHouseMapper.addIdent(storehouseIdentification));
    }

    @Override
    public ResultVo updateIdent(StorehouseIdentification storehouseIdentification) {
        //根据前端传过来的id查询出库房标识信息
        StorehouseIdentification ident = storeHouseMapper.queryIdentById(storehouseIdentification.getId());
        if (ident.getStatus()) {
            return ResultBuildVo.error("库房标识已启用，不可修改");
        }
        //数据校验 判断名称，详细地址是否为空
        if (StringUtils.isEmpty(storehouseIdentification.getName())) {
            return ResultBuildVo.error("库房名称不能为空");
        }
        //查询数据库中是否存在该名称
        StorehouseIdentification oldHouse = storeHouseMapper.queryIdentByName(storehouseIdentification.getName());
        if (oldHouse != null && !oldHouse.getId().equals(storehouseIdentification.getId())) {
            return ResultBuildVo.error("库房名称已存在");
        } else {
            storehouseIdentification.setUpdateMan("admin");
            return ResultBuildVo.success(storeHouseMapper.updateIdent(storehouseIdentification));
        }
    }

    @Override
    public ResultVo updateStatusIdent(StorehouseIdentification storehouseIdentification) {
        //数据校验 判断状态是否为空
        if (storehouseIdentification.getStatus() == null) {
            return ResultBuildVo.error("库房状态不能为空");
        }
        storehouseIdentification.setUpdateMan("admin");
        return ResultBuildVo.success(storeHouseMapper.updateStatusIdent(storehouseIdentification));
    }

    @Override
    public ResultVo queryArea() {
        CountryDownList countryDownList = new CountryDownList();
        List<String> regionList = storeHouseMapper.queryArea();
        List<String> countryList = storeHouseMapper.queryCountry();
        List<String> provinceList = storeHouseMapper.queryProvince();
        List<String> cityList = storeHouseMapper.queryCity();
        List<String> countyList = storeHouseMapper.queryCounty();
        countryDownList.setRegionList(regionList);
        countryDownList.setCountryList(countryList);
        countryDownList.setProvinceList(provinceList);
        countryDownList.setCityList(cityList);
        countryDownList.setCountyList(countyList);
        return ResultBuildVo.success(countryDownList);
    }
}
