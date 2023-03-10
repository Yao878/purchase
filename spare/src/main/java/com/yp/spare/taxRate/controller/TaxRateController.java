package com.yp.spare.taxRate.controller;

import com.yp.spare.taxRate.bean.TaxRate;
import com.yp.spare.taxRate.service.TaxRateService;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 税率税码controller层
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/21 15:14
 * 版本号:1.0
 * TODO:
 */

@CrossOrigin
@RequestMapping("/taxRate")
@RestController
public class TaxRateController {
    @Autowired
    private TaxRateService taxRateService;

    @RequestMapping("queryPage")
    @ApiOperation(value = "分页查询税率税码", notes = "分页查询税率税码")
    public ResultVo queryPage(@RequestBody QueryPageVo queryPageVo) {
        return taxRateService.queryPage(queryPageVo);
    }

    @RequestMapping("addTaxRate")
    @ApiOperation(value = "添加税率税码", notes = "添加税率税码")
    public ResultVo addTaxRate(@RequestBody TaxRate taxRate, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(taxRateService.addTaxRate(taxRate));
    }

    @RequestMapping("updateTaxRate")
    @ApiOperation(value = "修改税率税码", notes = "修改税率税码")
    public ResultVo updateTaxRate(@RequestBody TaxRate taxRate, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(taxRateService.updateTaxRate(taxRate));
    }

    @RequestMapping("updateStatus")
    @ApiOperation(value = "修改税率税码状态", notes = "修改税率税码状态")
    public ResultVo updateStatus(@RequestBody TaxRate taxRate, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(taxRateService.updateStatus(taxRate));
    }
}
