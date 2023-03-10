package com.yp.spare.taxRate.serviceImpl;

import com.yp.spare.taxRate.bean.TaxRate;
import com.yp.spare.taxRate.mapper.TaxRateMapper;
import com.yp.spare.taxRate.service.TaxRateService;
import com.yp.spare.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/21 15:15
 版本号:1.0
 */
@Service
public class TaxRateServiceImpl implements TaxRateService {
    @Autowired
    private TaxRateMapper taxRateMapper;

    @Override
    public ResultVo queryPage(QueryPageVo queryPageVo) {
        //计算分页数
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        //查询数据
        List<TaxRate> taxRateList = taxRateMapper.queryPage(queryPageVo);
        for (TaxRate taxRate : taxRateList) {
            if (taxRate.getStatus()) {
                taxRate.setStatusName("启用");
            } else {
                taxRate.setStatusName("禁用");
            }
        }
        //查询总数
        Integer totalCount = taxRateMapper.getCorFacCount(queryPageVo);
        return PageInfoUtil.getResultVoByTaxRate(queryPageVo, taxRateList, totalCount);
    }

    @Override
    public ResultVo addTaxRate(TaxRate taxRate) {
        //根据税码判断是否存在
        int codeCount = taxRateMapper.getTaxRateCountByCode(taxRate.getTaxCode());
        if (codeCount > 0) {
            return ResultBuildVo.error("税码已存在,请重新添加");
        }
        //根据税率描述判断是否存在
        int remarkCount = taxRateMapper.getTaxRateCountByRemark(taxRate.getTaxDec());
        if (remarkCount > 0) {
            return ResultBuildVo.error("税率描述已存在,请重新添加");
        }
        Date date = new Date();
        taxRate.setTaxRateAddTime(date).setTaxRateUpdateTime(date).setTaxRateAddUser("admin").setTaxRateUpdateUser("admin");
        taxRateMapper.addTaxRate(taxRate);
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateTaxRate(TaxRate taxRate) {
        //根据税率编码 查询当前状态
        TaxRate taxRate1 = taxRateMapper.getTaxRateByCode(taxRate.getTaxCode());
        //如果为启用状态，不允许修改 直接返回错误信息
        if (taxRate1.getStatus()) {
            return ResultBuildVo.error("当前状态为启用，不允许修改！");
        } else {
            //如果为禁用状态，允许修改 直接修改
            //根据描述判断是否重复
            int count = taxRateMapper.getTaxRateByDecAndCode(taxRate.getTaxDec(), taxRate.getTaxCode());
            if (count > 0) {
                return ResultBuildVo.error("当前数据名称重复，请重新修改！");
            } else {
                //修改数据
                taxRate.setTaxRateUpdateUser("admin").setTaxRateUpdateTime(new Date());
                taxRateMapper.updateTaxRate(taxRate);
            }
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateStatus(TaxRate taxRate) {
        //根据税码 查询当前状态
        int taxRate1 = taxRateMapper.updateStatus(taxRate);
        //如果为启用状态，不允许修改 直接返回错误信息
        if (taxRate1 < 1) {
            return ResultBuildVo.error("状态修改失败！");
        } else {
            return ResultBuildVo.success();
        }
    }
}
