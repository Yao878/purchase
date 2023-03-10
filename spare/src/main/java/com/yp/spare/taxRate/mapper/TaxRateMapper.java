package com.yp.spare.taxRate.mapper;

import com.yp.spare.taxRate.bean.TaxRate;
import com.yp.spare.utils.QueryPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/*
 创建人:唐山吴彦祖
 创建时间:2023/2/21 15:16
 版本号:1.0
 TODO:
 */
@Mapper
public interface TaxRateMapper {

    List<TaxRate> queryPage(QueryPageVo queryPageVo);

    Integer getCorFacCount(QueryPageVo queryPageVo);

    int getTaxRateCountByCode(String taxRateCode);

    int getTaxRateCountByRemark(String taxRateRemark);

    void addTaxRate(TaxRate taxRate);

    void updateTaxRate(TaxRate taxRate);

    TaxRate getTaxRateByCode(String taxCode);

    int getTaxRateByDecAndCode(String taxDec, String taxCode);

    int updateStatus(TaxRate taxRate);

    List<TaxRate> queryAllTaxType();
}
