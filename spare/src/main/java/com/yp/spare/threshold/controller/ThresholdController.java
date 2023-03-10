package com.yp.spare.threshold.controller;

import com.yp.spare.threshold.bean.Threshold;
import com.yp.spare.threshold.service.ThresholdService;
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
 * 阈值Controller
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/20 17:20
 * 版本号:1.0
 */

@CrossOrigin
@RequestMapping("threshold")
@RestController
public class ThresholdController {
    @Autowired
    private ThresholdService thresholdService;

    @RequestMapping("/queryPage")
    @ApiOperation(value = "阈值分页查询")
    public ResultVo queryPage(@RequestBody QueryPageVo queryPageVo) {
        return thresholdService.thresholdQueryPage(queryPageVo);
    }

    @RequestMapping("/addThreshold")
    @ApiOperation(value = "添加阈值")
    public ResultVo addThreshold(@RequestBody Threshold Threshold, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return thresholdService.addThreshold(Threshold);
    }

    @RequestMapping("/updateThreshold")
    @ApiOperation(value = "修改阈值")
    public ResultVo updateThreshold(@RequestBody Threshold Threshold, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return thresholdService.updateThreshold(Threshold);
    }

    @RequestMapping("/updateStatus")
    @ApiOperation(value = "修改阈值状态")
    public ResultVo updateStatus(@RequestBody Threshold Threshold, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return thresholdService.updateStatus(Threshold);
    }

}
