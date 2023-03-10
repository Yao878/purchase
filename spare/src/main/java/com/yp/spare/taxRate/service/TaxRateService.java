package com.yp.spare.taxRate.service;


import com.yp.spare.taxRate.bean.TaxRate;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultVo;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/21 15:15
 版本号:1.0
 TODO:
 */
public interface TaxRateService {
    ResultVo queryPage(QueryPageVo queryPageVo);

    ResultVo addTaxRate(TaxRate taxRate);

    ResultVo updateTaxRate(TaxRate taxRate);

    ResultVo updateStatus(TaxRate taxRate);
}
