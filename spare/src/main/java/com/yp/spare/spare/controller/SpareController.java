package com.yp.spare.spare.controller;

import com.yp.spare.spare.bean.Spare;
import com.yp.spare.spare.service.SpareService;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * 备件Controller层
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/13 22:39
 * 版本号:1.0
 */

@CrossOrigin
@RequestMapping("spare")
@RestController
public class SpareController {
    @Autowired
    private SpareService spareService;

    @RequestMapping("/queryPage")
    @ApiOperation(value = "备件分页查询")
    public ResultVo queryPage(@RequestBody QueryPageVo queryPageVo) {
        return spareService.spareQueryPage(queryPageVo);
    }

    @RequestMapping("/updateStatus")
    @ApiOperation(value = "修改备件状态")
    public ResultVo updateStatus(@RequestBody Spare spare) {
        return spareService.updateStatus(spare);
    }

    @RequestMapping("/addSpare")
    @ApiOperation(value = "添加备件")
    public ResultVo addSpare(@RequestBody Spare spare, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return spareService.addSpare(spare);
    }

    @PostMapping("/updateSpare")
    @ApiOperation(value = "修改备件")
    public ResultVo updateSpare(@RequestBody Spare spare, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return spareService.updateSpare(spare);
    }


}