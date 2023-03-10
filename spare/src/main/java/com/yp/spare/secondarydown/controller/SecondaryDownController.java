package com.yp.spare.secondarydown.controller;

import com.yp.spare.secondarydown.bean.SecondaryDown;
import com.yp.spare.secondarydown.mapper.SecondaryDownMapper;
import com.yp.spare.secondarydown.service.SecondaryDownService;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

/**
 * 二级下拉框
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/28 14:47
 * 版本号:1.0
 * 增加同级接口，增加下级接口，查询接口，启用禁用接口
 */
@RestController
@RequestMapping("/secondaryDown")
public class SecondaryDownController {
    @Autowired
    private SecondaryDownService secondaryDownService;
    @Autowired
    private SecondaryDownMapper secondaryDownMapper;

    @RequestMapping("/selectAll")
    public ResultVo selectAll() {
        return ResultBuildVo.success(secondaryDownService.selectAll());
    }

    //按照多个条件查询，结果放到map里
    @RequestMapping("/selectByCode")
    public ResultVo selectByCode(@RequestBody @Valid SecondaryDown secondaryDown) {
        return ResultBuildVo.success(secondaryDownService.selectByCode(secondaryDown));
    }

    @RequestMapping("/addParent")
    public ResultVo addParent(@RequestBody @Valid SecondaryDown secondaryDown, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(secondaryDownService.addParent(secondaryDown));
    }

    @RequestMapping("/addSon")
    public ResultVo addSon(@RequestBody @Valid SecondaryDown secondaryDown, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(secondaryDownService.addSon(secondaryDown));
    }

    @RequestMapping("/disable")
    public ResultVo disable(@RequestBody @Valid SecondaryDown secondaryDown) {
        return ResultBuildVo.success(secondaryDownService.disable(secondaryDown));
    }

    @RequestMapping("/enable")
    public ResultVo enable(@RequestBody @Valid SecondaryDown secondaryDown) {
        return ResultBuildVo.success(secondaryDownService.enable(secondaryDown));
    }
}
