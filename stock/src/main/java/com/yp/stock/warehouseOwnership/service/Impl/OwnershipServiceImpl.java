package com.yp.stock.warehouseOwnership.service.Impl;

import com.yp.stock.area.bean.Area;
import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.utils.AllRecordsVo;
import com.yp.stock.utils.ParamUtil;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import com.yp.stock.warehouseOwnership.bean.*;
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
        //计算分页用的参数值
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        if (queryPageVo.getCorName() != null) {
            queryPageVo.setCorCode(ownershipMapper.queryCorCode(queryPageVo.getCorName()));
        }
        List<FirstPageVo> firstPageVoList = ownershipMapper.queryFirstPage(queryPageVo);
        for (FirstPageVo a : firstPageVoList) {
            a.setStatusName(a.getStatus() ? "启用" : "禁用");
        }
        //查询总条数
        Integer totalCount = ownershipMapper.queryFirstPageCount(queryPageVo);
        //计算
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(ParamUtil.PAGE_COUNT);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % ParamUtil.PAGE_COUNT == 0 ? totalCount / ParamUtil.PAGE_COUNT : totalCount / ParamUtil.PAGE_COUNT + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(firstPageVoList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
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
        //如果法人Code不为空，就查询该法人下的库位
        else {
            //查询该法人下的库位信息
            List<Kuwei> kuweiList = ownershipMapper.queryKuweiListByCorCode(secendPageVo.getCorCode());
            //根据库位信息查询库区信息
            List<Area> areaList = ownershipMapper.queryAreaListByKuweiList(kuweiList);
            //根据库区信息查询库房信息
            List<StoreHouse> storeHouseList = ownershipMapper.queryStoreListByAreaList(areaList);
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
