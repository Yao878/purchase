package com.yp.spare.spareCostPrice.controller;

import com.yp.spare.spareCostPrice.bean.SpareCostPrice;
import com.yp.spare.spareCostPrice.bean.importSpare;
import com.yp.spare.spareCostPrice.service.SpareCostPriceService;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Objects;

/**
 * 备件成本价Controller层
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/22 10:46
 * 版本号:1.0
 */
@RequestMapping("/materialCostPrice")
@RestController
@CrossOrigin
public class SpareCostPriceController {
    @Autowired
    private SpareCostPriceService spareCostPriceService;

    @RequestMapping("/queryPage")
    @ApiOperation(value = "分页查询备件成本价", notes = "分页查询备件成本价")
    public ResultVo queryPage(@RequestBody QueryPageVo queryPageVo) {
        return ResultBuildVo.success(spareCostPriceService.queryPage(queryPageVo));
    }

    @RequestMapping("/addSpareCostPrice")
    @ApiOperation(value = "新增备件成本价", notes = "新增备件成本价")
    public ResultVo addSpareCostPrice(@RequestBody List<SpareCostPrice> spareCostPrice, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(spareCostPriceService.addSpareCostPrice(spareCostPrice));
    }

    @RequestMapping("/updateSpareCostPrice")
    @ApiOperation(value = "修改备件成本价", notes = "修改备件成本价")
    public ResultVo updateSpareCostPrice(@RequestBody SpareCostPrice spareCostPrice, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(spareCostPriceService.updateSpareCostPrice(spareCostPrice));
    }

    @RequestMapping("/updateStatus")
    @ApiOperation(value = "修改备件成本价状态", notes = "修改备件成本价状态")
    public ResultVo updateStatus(@RequestBody SpareCostPrice spareCostPrice, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(spareCostPriceService.updateStatus(spareCostPrice));
    }

    @PostMapping("/poiFile")
    @ApiOperation(value = "备件成本价导入")
    public ResultVo poiFile(@RequestBody MultipartFile file, String CorName, String priceType, String effectiveTime, String invalidTime) throws Exception {
        importSpare spareCondition = new importSpare();
        spareCondition.setCorName(CorName).setPriceType(priceType).setEffectiveTime(effectiveTime).setInvalidTime(invalidTime);
        return spareCostPriceService.poiFile(file, spareCondition);
    }

    @GetMapping("/exportFile")
    @ApiOperation(value = "导出备件成本价")
    public ResultVo exportFile(String Code, String name, HttpServletResponse response) throws Exception {
        SpareCostPrice subclassExportVo = new SpareCostPrice();
        subclassExportVo.setSpareCode(Code).setSpareName(name);
        return spareCostPriceService.exportFile(subclassExportVo, response);
    }
}
