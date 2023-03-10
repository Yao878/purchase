package com.yp.spare.spBroadCategory.controller;

import com.yp.spare.spBroadCategory.bean.SpBroadCategory;
import com.yp.spare.spBroadCategory.service.SpBroadCategoryService;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 备件大类Controller层
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/22 11:58
 * 版本号:1.0
 */
@RestController
@RequestMapping("/SpBroadCategory")
@Slf4j
@CrossOrigin//跨域
public class SpBroadCategoryController {
    @Autowired
    private SpBroadCategoryService spBroadCategoryService;

    //分页查询
    @RequestMapping("/getSpBroadCategoryByPage")
    @ApiOperation(value = "大类分页查询")
    public ResultVo getSpBroadCategoryByPage(@RequestBody QueryPageVo queryPageVo) {
        return spBroadCategoryService.getSpBroadCategoryByPage(queryPageVo);
    }

    //新增
    @RequestMapping("/addSpBroadCategory")
    @ApiOperation(value = "添加大类")
    public ResultVo addSpBroadCategory(@RequestBody @Valid SpBroadCategory spBroadCategory) {
        return ResultBuildVo.success(spBroadCategoryService.addSpBroadCategory(spBroadCategory));
    }


    //修改
    @RequestMapping("/updateSpBroadCategory")
    @ApiOperation(value = "修改大类")
    public ResultVo updateSpBroadCategory(@RequestBody SpBroadCategory spBroadCategory, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ResultBuildVo.success(spBroadCategoryService.updateSpBroadCategory(spBroadCategory));
        return ResultBuildVo.success();
    }

    //修改状态
    @RequestMapping("/updateSpBroadCategoryByEnable")
    @ApiOperation(value = "修改大类状态")
    public ResultVo updateSpBroadCategoryByStart(@RequestBody SpBroadCategory spBroadCategory) {
        return (ResultVo) spBroadCategoryService.updateSpBroadCategoryByEnable(spBroadCategory);
    }

    @GetMapping("/queryAll")
    @ApiOperation(value = "查询所有大类")
    public ResultVo queryAll() {
        return spBroadCategoryService.queryAll();
    }
}
