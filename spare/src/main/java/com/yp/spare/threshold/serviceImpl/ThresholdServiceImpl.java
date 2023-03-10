package com.yp.spare.threshold.serviceImpl;

import com.yp.spare.spLittleCategory.mapper.SpLittleCategoryMapper;
import com.yp.spare.spare.mapper.SpareMapper;
import com.yp.spare.threshold.bean.Threshold;
import com.yp.spare.threshold.mapper.ThresholdMapper;
import com.yp.spare.threshold.service.ThresholdService;
import com.yp.spare.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/20 17:30
 版本号:1.0
 */
@Service
public class ThresholdServiceImpl implements ThresholdService {
    @Autowired
    private ThresholdMapper thresholdMapper;
    @Autowired
    private SpareMapper spareMapper;
    @Autowired
    private SpLittleCategoryMapper spLittleCategoryMapper;

    @Override
    public ResultVo thresholdQueryPage(QueryPageVo queryPageVo) {
        //计算分页数
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        //查询数据
        List<Threshold> thresholds = thresholdMapper.getThresholdByPage(queryPageVo);
        for (Threshold threshold : thresholds) {
            if (threshold.getStatus()) {
                threshold.setStatusName("启用");
            } else {
                threshold.setStatusName("禁用");
            }
            if (threshold.getIsBorrow()) {
                threshold.setIsBorrowName("可借用");
            } else {
                threshold.setIsBorrowName("不可借用");
            }
            if (threshold.getIsSale()) {
                threshold.setIsSaleName("可销售");
            } else {
                threshold.setIsSaleName("不可销售");
            }
        }
        //查询总数
        Integer totalCount = thresholdMapper.getThresholdCount(queryPageVo);
        return PageInfoUtil.getResultVoByThreshold(queryPageVo, thresholds, totalCount);
    }

    @Override
    public ResultVo addThreshold(Threshold threshold) {
        Date date = new Date();
        //验证备件是否存在
        int nameCount = spareMapper.queryIdBySpare(threshold.getSpareId());
        if (nameCount <= 0) {
            return ResultBuildVo.error("该备件不存在，请重新选择");
        }
        //验证计划小类是否存在
        int littleCount = spLittleCategoryMapper.queryLittleCount(threshold.getSubClassId());
        if (littleCount <= 0) {
            return ResultBuildVo.error("该计划小类不存在");
        }
        threshold.setAddtime(date).setUpdatetime(date).setAddman("admin")
                .setUpdateman("admin").setIsBorrow(false).setStatus(false)
                .setIsSale(false).setVersion(1);
        return ResultBuildVo.success(thresholdMapper.addThreshold(threshold));
    }

    @Override
    public ResultVo updateThreshold(Threshold threshold) {
        //验证备件是否存在
        int nameCount = spareMapper.queryIdBySpare(threshold.getSpareId());
        if (nameCount <= 0) {
            return ResultBuildVo.error("该备件不存在，请重新选择");
        }
        //验证计划小类是否存在
        int littleCount = spLittleCategoryMapper.queryLittleCount(threshold.getSubClassId());
        if (littleCount <= 0) {
            return ResultBuildVo.error("该计划小类不存在");
        }
        threshold.setUpdatetime(new Date()).setUpdateman("admin");
        int count = thresholdMapper.updateThreshold(threshold);
        if (count > 0) {
            return ResultBuildVo.success("修改成功");
        } else {
            return ResultBuildVo.error("修改失败");
        }
    }

    @Override
    public ResultVo updateStatus(Threshold threshold) {
        int count = thresholdMapper.updateStatus(threshold);
        if (count > 0) {
            return ResultBuildVo.success("修改成功");
        } else {
            return ResultBuildVo.error("修改失败");
        }
    }
}
