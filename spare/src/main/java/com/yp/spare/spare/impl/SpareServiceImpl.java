package com.yp.spare.spare.impl;

import com.yp.spare.spBroadCategory.mapper.SpBroadCategoryMapper;
import com.yp.spare.spLittleCategory.mapper.SpLittleCategoryMapper;
import com.yp.spare.spare.bean.Spare;
import com.yp.spare.spare.bean.SpareCor;
import com.yp.spare.spare.mapper.SpareMapper;
import com.yp.spare.spare.service.SpareService;
import com.yp.spare.utils.PageInfoUtil;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/14 10:20
 版本号:1.0
 */
@Service
public class SpareServiceImpl implements SpareService {
    @Autowired
    private SpareMapper spareMapper;
    @Autowired
    private SpLittleCategoryMapper spLittleCategoryMapper;
    @Autowired
    private SpBroadCategoryMapper spBroadCategoryMapper;

    @Override
    public ResultVo spareQueryPage(QueryPageVo queryPageVo) {
        //计算分页用的参数值
        Integer offSize = (queryPageVo.getPageIndex() - 1) * 100;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(100);
        //查询出的数据
        List<Spare> SpareList = spareMapper.queryPage(queryPageVo);
        List<Integer> SpareIds = new ArrayList<>();
        for (Spare sub : SpareList) {
            if (sub.getStatus()) {
                sub.setStatusName("启用");
            } else {
                sub.setStatusName("禁用");
            }
            //将备件ID取出来
            SpareIds.add(sub.getId());
        }
////        查询法人信息
//        List<SpareCorByName> spList = spareMapper.getSparePeronList(SpareIds);
////        处理数据
//        for (Spare sp : SpareList) {
//            //定义一个字段用来存放法人信息
//            StringBuilder sb = new StringBuilder();
//            for (SpareCorByName spc : spList) {
//                if (sp.getId().equals(spc.getSpareId())) {
//                    sb.append(spc.getLegalPersonName()).append(",");
//                }
//            }
//            sp.setLegalPersonName(sb.substring(0, sb.toString().length() - 1));
////            sp.setLegalPersonName(String.join(",", sb));
//        }
        //查询总条数
        Integer totalCount = spareMapper.queryCount(queryPageVo);
        //计算
        return PageInfoUtil.getResultVoBySpare(queryPageVo, SpareList, totalCount);
    }

    @Override
    public ResultVo updateStatus(Spare spare) {
        int i = spareMapper.updateStatus(spare);
        if (i < 1) {
            return ResultBuildVo.error("修改失败");
        } else {
            return ResultBuildVo.success();
        }
    }

    @Override
    @Transactional
    public ResultVo addSpare(Spare spare) {
        int nameCount = spareMapper.queryNameCount(spare.getName(), spare.getId());
        if (nameCount > 0) {
            return ResultBuildVo.error("该备件名称已存在");
        }
        int codeCount = spareMapper.queryCodeCount(spare.getCode());
        if (codeCount > 0) {
            return ResultBuildVo.error("该备件编码已存在");
        }
        //验证计划大类是否存在
        int broadCount = spBroadCategoryMapper.queryBroadCount(spare.getPlanClassId());
        if (broadCount <= 0) {
            return ResultBuildVo.error("该计划大类不存在");
        }
        //验证计划小类是否存在
        int littleCount = spLittleCategoryMapper.queryLittleCount(spare.getSubClassId());
        if (littleCount <= 0) {
            return ResultBuildVo.error("该计划小类不存在");
        }
        //新增数据(备件表)
        Date date = new Date();
        spare.setAddman("admin").setUpdateman("admin").setAddtime(date).setUpdatetime(date);
        spareMapper.addSpare(spare);
        //新增备件法人表信息(在addSpare.map中返回id信息)
        Integer spareId = spare.getId();
        //新增中间表关系(批量添加)
        if (spare.getLegalPersonIds() != null) {
            SpareCor spareCor = new SpareCor();
            spareCor.setSpareId(spareId);
            spareCor.setLegalPersonIds(spare.getLegalPersonIds());
            spareMapper.addSpareCor(spareCor);
        }
        return ResultBuildVo.success();
    }

    @Transactional
    @Override
    public ResultVo updateSpare(Spare spare) {
        int nameCount = spareMapper.queryNameCount(spare.getName(), spare.getId());
        if (nameCount > 0) {
            return ResultBuildVo.error("该备件已存在");
        } else {
            //修改
            //验证计划大类是否存在
            int broadCount = spBroadCategoryMapper.queryBroadCount(spare.getPlanClassId());
            if (broadCount <= 0) {
                return ResultBuildVo.error("该计划大类不存在");
            }
            //验证计划小类是否存在
            int littleCount = spLittleCategoryMapper.queryLittleCount(spare.getSubClassId());
            if (littleCount <= 0) {
                return ResultBuildVo.error("该计划小类不存在");
            }
            spareMapper.updateSpare(spare);
            //新增数据
            //删除法人中间表
            spareMapper.deleteSpareAndCor(spare.getId());
            if (spare.getLegalPersonId() != null) {
                //新增法人中间表
                spareMapper.addSpareAndCor(spare.getId(), spare.getLegalPersonId());
            }
        }
        return ResultBuildVo.success();
    }


}
