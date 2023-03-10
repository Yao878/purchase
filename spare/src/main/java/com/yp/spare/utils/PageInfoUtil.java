package com.yp.spare.utils;


import com.yp.spare.corfac.bean.CorFac;
import com.yp.spare.spBroadCategory.bean.SpBroadCategory;
import com.yp.spare.spLittleCategory.bean.SpLittleCategory;
import com.yp.spare.spare.bean.Spare;
import com.yp.spare.spareCostPrice.bean.SpareCostPrice;
import com.yp.spare.taxRate.bean.TaxRate;
import com.yp.spare.threshold.bean.Threshold;

import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/10 17:49
 版本号:1.0
 */
public class PageInfoUtil {
    public static ResultVo getResultVoByLittle(QueryPageVo queryPageVo, List<SpLittleCategory> subclassList, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(ParamUtil.PAGE_COUNT);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % ParamUtil.PAGE_COUNT == 0 ? totalCount / ParamUtil.PAGE_COUNT : totalCount / ParamUtil.PAGE_COUNT + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(subclassList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    public static ResultVo getResultVoByBroad(QueryPageVo queryPageVo, List<SpBroadCategory> subclassList, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(ParamUtil.PAGE_COUNT);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % ParamUtil.PAGE_COUNT == 0 ? totalCount / ParamUtil.PAGE_COUNT : totalCount / ParamUtil.PAGE_COUNT + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(subclassList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    public static ResultVo getResultVoByCorFac(QueryPageVo queryPageVo, List<CorFac> corFacList, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(ParamUtil.PAGE_COUNT);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % ParamUtil.PAGE_COUNT == 0 ? totalCount / ParamUtil.PAGE_COUNT : totalCount / ParamUtil.PAGE_COUNT + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(corFacList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    public static ResultVo getResultVoBySpare(QueryPageVo queryPageVo, List<Spare> subclassList, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(10);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(subclassList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    public static ResultVo getResultVoByThreshold(QueryPageVo queryPageVo, List<Threshold> thresholds, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(10);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(thresholds);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    public static ResultVo getResultVoByTaxRate(QueryPageVo queryPageVo, List<TaxRate> taxRateList, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(10);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(taxRateList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    public static ResultVo getResultVoByPrice(com.yp.spare.spareCostPrice.bean.QueryPageVo queryPageVo, List<SpareCostPrice> spareCostPrices, Integer totalCount) {
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(10);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % 10 == 0 ? totalCount / 10 : totalCount / 10 + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(spareCostPrices);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }
}
