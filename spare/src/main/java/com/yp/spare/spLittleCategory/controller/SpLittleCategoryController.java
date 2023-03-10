package com.yp.spare.spLittleCategory.controller;

import com.yp.spare.spLittleCategory.bean.SpLittleCategory;
import com.yp.spare.spLittleCategory.bean.SubclassExportVo;
import com.yp.spare.spLittleCategory.service.SpLittleCategoryService;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Objects;

/**
 * 备件小类Controller层
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/21 20:54
 * 版本号:1.0
 */
@RestController
@RequestMapping("/SpLittleCategory")
@Slf4j
@CrossOrigin//跨域
public class SpLittleCategoryController {

    @Autowired
    private SpLittleCategoryService spLittleCategoryService;

    //分页
    @RequestMapping("/getSpLittleCategoryByPage")
    @ApiOperation(value = "备件小类分页查询")
    public ResultVo getSpLittleCategoryByPage(@RequestBody QueryPageVo queryPageVo) {
        return spLittleCategoryService.getSpLittleCategoryByPage(queryPageVo);
    }


    //新增
    @RequestMapping("/addSpLittleCategory")
    @ApiOperation(value = "新增备件小类")
    public ResultVo addSpLittleCategory(@RequestBody @Valid SpLittleCategory spLittleCategory, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(spLittleCategoryService.addSpLittleCategory(spLittleCategory));
    }

    //修改
    @RequestMapping("/updateSpLittleCategory")
    @ApiOperation(value = "修改备件小类")
    public ResultVo updateSpLittleCategory(@RequestBody @Valid SpLittleCategory spLittleCategory, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ResultBuildVo.success(spLittleCategoryService.updateSpLittleCategory(spLittleCategory));
        return ResultBuildVo.success();
    }

    //启用,禁用
    @RequestMapping("/updateSpLittleCategoryByEnable")
    @ApiOperation(value = "启用,禁用")
    public ResultVo updateSpLittleCategoryByStart(@RequestBody SpLittleCategory spLittleCategory) {
        return spLittleCategoryService.updateSpLittleCategoryByEnable(spLittleCategory);
    }

    @GetMapping("/queryAll")
    @ApiOperation(value = "查询所有备件小类")
    public ResultVo queryAll() {
        return spLittleCategoryService.queryAll();
    }

    @RequestMapping("/poiFile")
    @ApiOperation(value = "导入备件小类")
    public ResultVo poiFile(@RequestBody MultipartFile file) throws Exception {
        return spLittleCategoryService.poiFile(file);
    }

    @GetMapping("/exportFile")
    @ApiOperation(value = "导出备件小类")
    public ResultVo exportFile(String Code, String name, Boolean status, HttpServletResponse response) throws Exception {
        SubclassExportVo subclassExportVo = new SubclassExportVo();
        subclassExportVo.setCode(Code).setName(name).setStatus(status);
        return spLittleCategoryService.exportFile(subclassExportVo, response);
    }

}
