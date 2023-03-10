package com.yp.stock.storeHouse.controller;

import com.yp.stock.storeHouse.bean.IdentQueryPageVo;
import com.yp.stock.storeHouse.bean.QueryPageVo;
import com.yp.stock.storeHouse.bean.StoreHouse;
import com.yp.stock.storeHouse.bean.StorehouseIdentification;
import com.yp.stock.storeHouse.service.StoreHouseService;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 库房管理
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/7 19:24
 * 版本号:1.0
 */
@RestController
@RequestMapping("storeHouse")
@CrossOrigin
public class StoreHouseController {
    @Autowired
    private StoreHouseService storeHouseService;

    @RequestMapping("/queryPage")
    @ApiOperation(value = "库房分页查询")
    public ResultVo queryPage(@Valid @RequestBody QueryPageVo queryPageVo) {
        if (queryPageVo.getPageSize() == 0) {
            queryPageVo.setPageSize(10);
        }
        return storeHouseService.queryPage(queryPageVo);
    }

    @RequestMapping("/add")
    @ApiOperation(value = "添加库房")
    public ResultVo add(@Valid @RequestBody StoreHouse storeHouse, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return storeHouseService.add(storeHouse);
    }

    @RequestMapping("/update")
    @ApiOperation(value = "修改库房")
    public ResultVo update(@Valid @RequestBody StoreHouse storeHouse, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return storeHouseService.update(storeHouse);
    }

    @RequestMapping("/updateStatus")
    @ApiOperation(value = "修改库房状态")
    public ResultVo updateStatus(@Valid @RequestBody StoreHouse storeHouse, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return storeHouseService.updateStatus(storeHouse);
    }

    @RequestMapping("/queryPageIdent")
    @ApiOperation(value = "库房标识分页查询")
    public ResultVo queryPageIdent(@Valid @RequestBody IdentQueryPageVo queryPageVo) {
        if (queryPageVo.getPageSize() == 0) {
            queryPageVo.setPageSize(10);
        }
        return storeHouseService.queryPageIdent(queryPageVo);
    }

    @RequestMapping("/addIdent")
    @ApiOperation(value = "添加库房标识")
    public ResultVo addIdent(@Valid @RequestBody StorehouseIdentification storehouseIdentification, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return storeHouseService.addIdent(storehouseIdentification);
    }

    @RequestMapping("/updateIdent")
    @ApiOperation(value = "修改库房标识")
    public ResultVo updateIdent(@Valid @RequestBody StorehouseIdentification storehouseIdentification, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return storeHouseService.updateIdent(storehouseIdentification);
    }

    @RequestMapping("/updateStatusIdent")
    @ApiOperation(value = "修改库房标识状态")
    public ResultVo updateStatusIdent(@Valid @RequestBody StorehouseIdentification storehouseIdentification, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return storeHouseService.updateStatusIdent(storehouseIdentification);
    }

    //地区下五个拉框
    @GetMapping("/queryArea")
    @ApiOperation(value = "地区下拉框")
    public ResultVo queryArea() {
        return storeHouseService.queryArea();
    }
}
