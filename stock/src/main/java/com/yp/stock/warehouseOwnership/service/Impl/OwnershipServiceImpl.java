package com.yp.stock.warehouseOwnership.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yp.stock.area.bean.Area;
import com.yp.stock.area.bean.AreaAttribute;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.BigBean;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.ShipVo;
import com.yp.stock.warehouseOwnership.mapper.OwnershipMapper;
import com.yp.stock.warehouseOwnership.service.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * 爱的
 */

@Service
public class OwnershipServiceImpl implements OwnershipService {
    @Autowired
    private OwnershipMapper ownershipMapper;

    @Override
    public ResultVo queryFirstPage(ShipVo shipVo) {
        PageHelper.startPage(1, 10);
        List<ShipVo> of = PageInfo.of(ownershipMapper.queryFirstPage(shipVo)).getList();
        for (ShipVo a : of) {
            a.setStatusName(a.getStatus() ? "启用" : "禁用");
        }
        //赋值
        return ResultBuildVo.success(of);
    }


    @Override
    public ResultVo addOwnership(Ownership ownership) {
        int add = ownershipMapper.addOwnership(ownership);
        if (add > 0) {
            return ResultBuildVo.success(add);
        }
        return ResultBuildVo.error("添加失败");
    }

    @Override
    public ResultVo querySecondPageByStorehouse(BigBean bigBean) {
        //根据查询条件查询信息
        if (bigBean.getLegalPersonCode() != null) {
            List<Kuwei> kuweiList = ownershipMapper.selectKuweiByLegalPersonId(bigBean.getLegalPersonCode());
            bigBean.setKuweiList(kuweiList);
            List<Area> areaList = ownershipMapper.selectAreaByKuweiList(kuweiList);//根据库位的库区外键查询库区 for（item.外键）
            bigBean.setAreaList(areaList);
            List<StoreHouse> storeHouseList = ownershipMapper.selectStoreHouseByAreaList(areaList);//根据库区的仓库外键查询仓库 for（item.外键）
            bigBean.setStoreHouseList(storeHouseList);
        } else {
            //查询条件所有库房
            List<StoreHouse> storeHouseList = ownershipMapper.selectStoreHouse(bigBean);
            bigBean.setStoreHouseList(storeHouseList);
            List<AreaAttribute> attributeList = bigBean.getAttributeList();
            if (attributeList != null) {
                List<Area> areaList = ownershipMapper.queryAreaList(storeHouseList, attributeList);
                bigBean.setAreaList(areaList);
                List<Kuwei> kuweiList = ownershipMapper.selectKuwei(areaList);
                bigBean.setKuweiList(kuweiList);
            } else {
                List<Area> areaList = ownershipMapper.selectAreaByStoreHouseList(storeHouseList);
                bigBean.setAreaList(areaList);
                List<Kuwei> kuweiList = ownershipMapper.selectKuwei(areaList);
                bigBean.setKuweiList(kuweiList);
            }
        }
        //前端联动查询
        return ResultBuildVo.success(bigBean);
    }

    @Override
    public ResultVo updateStatus(Ownership ownership) {
        int update = ownershipMapper.updateStatus(ownership);
        if (update > 0) {
            return ResultBuildVo.success(update);
        } else {
            return ResultBuildVo.error("修改失败");
        }
    }

    @Override
    public ResultVo queryBusiness(String areaCode, String businessCode) {
        return ResultBuildVo.success(ownershipMapper.queryBusiness(areaCode, businessCode));
    }

    @Override
    public ResultVo update(BigBean bigBean) {
        Integer flag = ownershipMapper.updateCorWarehouse(bigBean.getKuweiList());
        if (flag > 0) {
            return ResultBuildVo.success("  修改成功" + flag);
        } else {
            return ResultBuildVo.error("修改失败");
        }
    }
}
