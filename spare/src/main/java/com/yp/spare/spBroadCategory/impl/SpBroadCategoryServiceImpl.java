package com.yp.spare.spBroadCategory.impl;/*
 创建人:唐山吴彦祖
 创建时间:2023/1/27 14:48
 版本号:1.0
 */

import com.yp.spare.spBroadCategory.bean.SpBroadCategory;
import com.yp.spare.spBroadCategory.mapper.SpBroadCategoryMapper;
import com.yp.spare.spBroadCategory.service.SpBroadCategoryService;
import com.yp.spare.utils.*;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Accessors(chain = true)//链式写法
public class SpBroadCategoryServiceImpl implements SpBroadCategoryService {
    @Autowired
    private SpBroadCategoryMapper spBroadCategoryMapper;


    @Override
    public ResultVo addSpBroadCategory(SpBroadCategory spBroadCategory) {
        //判断是否存在相同的分类编码
        int codeCount = spBroadCategoryMapper.getSpBroadCategoryByCode(spBroadCategory.getCode());
        if (codeCount > 0) {
            return ResultBuildVo.error("分类编码已存在");
        }
        //判断是否存在相同的分类名称
        int nameCount = spBroadCategoryMapper.getSpBroadCategoryByName(spBroadCategory.getName());
        if (nameCount > 0) {
            return ResultBuildVo.error("分类名称已存在");
        }
        //新增
        Date date = new Date();
        spBroadCategory.setCategoryAddTime(date).setCategoryUpdateTime(date).setCategoryUpdateMan("admin").setCategoryAddMan("admin");
        spBroadCategoryMapper.addSpBroadCategory(spBroadCategory);

        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateSpBroadCategory(SpBroadCategory spBroadCategory) {
        //根据id查询出原来的数据
        SpBroadCategory oldSpBroadCategory = spBroadCategoryMapper.getSpBroadCategoryById(spBroadCategory.getId());
        //如果为启用状态，不允许修改
        if (oldSpBroadCategory.getStatus()) {
            return ResultBuildVo.error("启用状态不允许修改");
        } else {
            //根据名称判断是否重复
            int nameCount = spBroadCategoryMapper.getSpBroadCategoryByNameAndId(spBroadCategory.getName(), spBroadCategory.getId());
            if (nameCount > 0) {
                return ResultBuildVo.error("分类名称已存在");
            } else {
                spBroadCategory.setCategoryUpdateMan("admin");
                spBroadCategoryMapper.updateSpLittleCategory(spBroadCategory);
            }
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateSpBroadCategoryByEnable(SpBroadCategory spBroadCategory) {
        int count = spBroadCategoryMapper.updateSpBroadCategoryByEnable(spBroadCategory);
        if (count < 1) {
            return ResultBuildVo.error("状态修改失败！");
        } else {
            return ResultBuildVo.success();
        }
    }

    @Override
    public ResultVo queryAll() {
        List<SpBroadCategory> spBroadCategoryList = spBroadCategoryMapper.queryAll();
        return ResultBuildVo.success(spBroadCategoryList);
    }

    @Override
    public ResultVo getSpBroadCategoryByPage(QueryPageVo queryPageVo) {
        //计算出分页用的参数
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize).setPageSize(ParamUtil.PAGE_COUNT);
        //查询出的数据
        List<SpBroadCategory> subclassList = spBroadCategoryMapper.queryPage(queryPageVo);
        for (SpBroadCategory sub : subclassList) {
            if (sub.getStatus()) {
                sub.setStatusName("启用");
            } else {
                sub.setStatusName("禁用");
            }
        }
        //查询总条数
        Integer totalCount = spBroadCategoryMapper.queryPageCount(queryPageVo);
        //封装返回的数据
        return PageInfoUtil.getResultVoByBroad(queryPageVo, subclassList, totalCount);
    }
}
