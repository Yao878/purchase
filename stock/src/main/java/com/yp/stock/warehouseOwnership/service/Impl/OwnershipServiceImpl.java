package com.yp.stock.warehouseOwnership.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yp.stock.area.bean.Area;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.BigBean;
import com.yp.stock.warehouseOwnership.bean.Ownership;
import com.yp.stock.warehouseOwnership.bean.QueryPageVo;
import com.yp.stock.warehouseOwnership.bean.SecendPageVo;
import com.yp.stock.warehouseOwnership.mapper.OwnershipMapper;
import com.yp.stock.warehouseOwnership.service.OwnershipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnershipServiceImpl implements OwnershipService {
    @Autowired
    private OwnershipMapper ownershipMapper;

    @Override
    public ResultVo queryFirstPage(QueryPageVo queryPageVo) {
        PageHelper.startPage(queryPageVo.getPageIndex(), queryPageVo.getPageSize());
        List<QueryPageVo> of = PageInfo.of(ownershipMapper.queryFirstPage(queryPageVo)).getList();
        for (QueryPageVo a : of) {
            a.setStatusName(a.getStatus() ? "启用" : "禁用");
        }
        //赋值
        return ResultBuildVo.success(of);
    }

    @Override
    public ResultVo querySecondPage(SecendPageVo secendPageVo) {
        //如果法人Code为空，就查询全部库房
        if (secendPageVo.getCorCode() == null) {
            //查询全部库房信息
            List<StoreHouse> storeHouseList = ownershipMapper.queryStoreList(secendPageVo);
            //查询全部库区信息
            List<Area> areaList = ownershipMapper.queryAreaList(secendPageVo);
            //查询全部库位信息
            List<Kuwei> kuweiList = ownershipMapper.queryKuweiList(secendPageVo);
            //将三个集合放到实体类中
            BigBean bigBean = new BigBean();
            bigBean.setStoreHouseList(storeHouseList).setKuweiList(kuweiList).setAreaList(areaList);
            return ResultBuildVo.success(bigBean);
        }
        //如果法人Code不为空，就查询该法人下的库房
        else {
            //查询该法人下的库房信息
            List<StoreHouse> storeHouseList = ownershipMapper.queryStoreListByStorehouse(secendPageVo);
            //查询该法人下的库区信息
            List<Area> areaList = ownershipMapper.queryAreaListByStorehouseList(storeHouseList);
            //查询该法人下的库位信息
            List<Kuwei> kuweiList = ownershipMapper.queryKuweiListByAreaList(areaList);
            //将三个集合放到实体类中
            BigBean bigBean = new BigBean();
            bigBean.setStoreHouseList(storeHouseList).setKuweiList(kuweiList).setAreaList(areaList);
            return ResultBuildVo.success(bigBean);
        }
    }

    @Override
    public ResultVo updateSituation(Ownership ownership) {
        List<String> slist;
        Ownership newOwnership = new Ownership();
        if (ownership.getStoreCode().size() > 0) {
            slist = ownershipMapper.updateSituationByStoreCode(ownership.getStoreCode());
            return ResultBuildVo.success(slist);
        } else if (ownership.getAreaCode().size() > 0) {
            slist = ownershipMapper.updateSituationByAreaCode(ownership.getAreaCode());
            return ResultBuildVo.success(slist);
        } else if (!ownership.getCorCode().isEmpty()) {
            newOwnership.setStoreCode(ownershipMapper.updateStoreSituationByCorCode(ownership.getCorCode()));
            newOwnership.setAreaCode(ownershipMapper.updateAreaSituationByCorCode(ownership.getCorCode()));
            newOwnership.setWarehouseCode(ownershipMapper.updateWareSituationByCorCode(ownership.getCorCode()));
            return ResultBuildVo.success(newOwnership);
        }
        return ResultBuildVo.success(newOwnership);
    }

    @Override
    public ResultVo addOwnership(Ownership ownership) {
        if (ownership.getCorCode().isEmpty()) {
            return ResultBuildVo.error("NO CorCode");
        }
        if (ownership.getWarehouseCode().isEmpty()) {
            ownershipMapper.deleteAllOwnershipByCorCode(ownership.getCorCode());
            return ResultBuildVo.success();
        }
        ownershipMapper.addOwnership(ownership.getCorCode(), ownership.getWarehouseCode());
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo querySecondPageByStorehouse(SecendPageVo secendPageVo) {
        //通过传来的库房编码和库房名称查询库房信息
        List<StoreHouse> storeHouseList = ownershipMapper.queryStoreListByStorehouse(secendPageVo);
        //根据库房信息查询库区信息
        List<Area> areaList = ownershipMapper.queryAreaListByStorehouseList(storeHouseList);
        //根据库区信息查询库位信息
        List<Kuwei> kuweiList = ownershipMapper.queryKuweiListByAreaList(areaList);
        //将三个集合放到实体类中
        BigBean bigBean = new BigBean();
        bigBean.setStoreHouseList(storeHouseList).setKuweiList(kuweiList).setAreaList(areaList);
        return ResultBuildVo.success(bigBean);
    }
}
