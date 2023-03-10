package com.yp.spare.corfac.controller;

import com.yp.spare.corfac.bean.CorFac;
import com.yp.spare.corfac.mapper.CorFacMapper;
import com.yp.spare.corfac.service.CorFacService;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.Objects;

/**
 * 法人工厂Controller层
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/10 20:31
 * 版本号:1.0
 */
@RestController
@RequestMapping("/CorFac")
@Slf4j
@CrossOrigin//跨域
public class CorFacController {
    @Autowired
    private CorFacService corFacService;
    @Autowired
    private CorFacMapper corFacMapper;

    //分页查询
    @RequestMapping("/getCorFacByPage")
    @ApiOperation(value = "法人工厂分页查询")
    public ResultVo getCorFacByPage(@RequestBody QueryPageVo queryPageVo, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.setAttribute("loginTime", new Date());
        System.out.println("loginTime:" + session.getAttribute("loginTime"));
        return corFacService.getCorFacByPage(queryPageVo);
    }

    //新增
    @RequestMapping("/addCorFac")
    @ApiOperation(value = "法人工厂新增")
    public ResultVo addCorFac(@RequestBody CorFac corFac, BindingResult bindingResult) {
        //数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        return ResultBuildVo.success(corFacService.addCorFac(corFac));
    }

    //修改
    @RequestMapping("/updateCorFac")
    @ApiOperation(value = "法人工厂修改")
    public ResultVo updateCorFac(@RequestBody CorFac corFac, BindingResult bindingResult) {
        //1.数据校验
        if (bindingResult.hasErrors()) {
            return ResultBuildVo.error(Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        ResultBuildVo.success(corFacService.updateCorFac(corFac));
        return ResultBuildVo.success();
    }

    //启用，禁用
    @RequestMapping("/updateStatus")
    @ApiOperation(value = "法人工厂启用，禁用")
    public ResultVo updateStatus(@RequestBody CorFac corFac) {
        return ResultBuildVo.success(corFacService.updateStatus(corFac));
    }

    //工厂下拉框
    @GetMapping("/queryPlantListByLegalPersonCode")
    @ApiOperation(value = "工厂下拉框")
    public ResultVo queryPlantListByLegalPersonCode() {
        return ResultBuildVo.success(corFacMapper.queryPlantListByLegalPersonCode("C0001", "C0002"));
    }
}
