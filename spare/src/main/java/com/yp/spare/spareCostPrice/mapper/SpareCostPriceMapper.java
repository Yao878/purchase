package com.yp.spare.spareCostPrice.mapper;

import com.yp.spare.spareCostPrice.bean.QueryPageVo;
import com.yp.spare.spareCostPrice.bean.SpareCostPrice;
import com.yp.spare.spareCostPrice.bean.importSpare;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/22 14:28
 * 版本号:1.0
 */
@Mapper
public interface SpareCostPriceMapper {

    List<SpareCostPrice> queryPage(QueryPageVo queryPageVo);

    Integer getPriceCount(QueryPageVo queryPageVo);

    void addSpareCostPrice(SpareCostPrice spareCostPrice);

    Integer getSpareCorFacCount(@Param("spareCode") String spareCode, @Param("corId") Integer corId, @Param("facCode") String facCode);


    void updateSpareCostPrice(SpareCostPrice spareCostPrice);

    List<SpareCostPrice> getSpareCorFac(@Param("spareId") Integer spareId, @Param("corId") Integer corId, @Param("facCode") String facCode);

    Integer updateStatus(Integer spareCostPriceId);

    Integer addSpareCostPriceList(List<SpareCostPrice> spareCostList);

    List<SpareCostPrice> selectSparePrice(importSpare spareCondition);

    List<SpareCostPrice> exportFile(SpareCostPrice subclassExportVo);
}
