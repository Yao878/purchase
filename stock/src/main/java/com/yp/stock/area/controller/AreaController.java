package com.yp.stock.area.controller;

import com.yp.stock.area.bean.Area;
import com.yp.stock.area.bean.AreaAttribute;
import com.yp.stock.area.bean.AreaAttributeQueryPageVo;
import com.yp.stock.area.bean.AreaQueryPageVo;
import com.yp.stock.area.service.AreaService;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 库区层
 */

@CrossOrigin
@RequestMapping("area")
@RestController
public class AreaController {
    @Autowired
    private AreaService areaService;

    //库区属性增删改查

    @RequestMapping("/addAreaAttribute")
    @ApiOperation(value = "添加库区属性")
    public ResultVo addAreaAttribute(@Valid @RequestBody AreaAttribute areaAttribute, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return areaService.addAreaAttribute(areaAttribute);
    }

    @RequestMapping("/updateAreaAttributeStatus")
    @ApiOperation(value = "修改库区属性状态")
    public ResultVo updateAreaAttributeStatus(@Valid @RequestBody AreaAttribute areaAttribute) {
        return areaService.updateAreaAttributeStatus(areaAttribute);
    }

    @PostMapping("/updateAreaAttribute")
    @ApiOperation(value = "修改库区属性")
    public ResultVo updateAreaAttribute(@Valid @RequestBody AreaAttribute areaAttribute, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return areaService.updateAreaAttribute(areaAttribute);
    }

    @RequestMapping("/queryAreaAttributePage")
    @ApiOperation(value = "库区属性分页查询")
    public ResultVo queryAreaAttributePage(@Valid @RequestBody AreaAttributeQueryPageVo queryPageVo) {
        return areaService.queryAreaAttributePage(queryPageVo);
    }

    //库区增删改查

    @RequestMapping("/addArea")
    @ApiOperation(value = "添加库区")
    public ResultVo addArea(@Valid @RequestBody Area area, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        return areaService.addArea(areaList);
    }

    @RequestMapping("/updateAreaStatus")
    @ApiOperation(value = "修改库区状态")
    public ResultVo updateAreaStatus(@Valid @RequestBody Area area) {
        return areaService.updateAreaStatus(area);
    }

    @PostMapping("/updateArea")
    @ApiOperation(value = "修改库区")
    public ResultVo updateArea(@Valid @RequestBody Area area, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return areaService.updateArea(area);
    }

    @RequestMapping("/queryAreaPage")
    @ApiOperation(value = "库区分页查询")
    public ResultVo queryAreaPage(@Valid @RequestBody AreaQueryPageVo queryPageVo) {
        return areaService.queryAreaPage(queryPageVo);
    }

    //库区导入
    @PostMapping("import")
    @ApiOperation(value = "库区导入")
    public ResultVo importFile(MultipartFile file) throws Exception {
        return areaService.importFile(file);
    }


}