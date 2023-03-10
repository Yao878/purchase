package com.yp.spare.corfac.impl;

import com.yp.spare.corfac.bean.CorFac;
import com.yp.spare.corfac.mapper.CorFacMapper;
import com.yp.spare.corfac.service.CorFacService;
import com.yp.spare.utils.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/10 20:32
 版本号:1.0
 */
@Service
public class CorFacServiceImpl implements CorFacService {
    @Autowired
    private CorFacMapper corFacMapper;

    @Override
    public ResultVo getCorFacByPage(QueryPageVo queryPageVo) {
        //计算分页数
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        //查询数据
        List<CorFac> corFacList = corFacMapper.getCorFacByPage(queryPageVo);
        for (CorFac corFac : corFacList) {
            if (corFac.getEnabled()) {
                corFac.setStatusName("启用");
            } else {
                corFac.setStatusName("禁用");
            }
        }
        //查询总数
        Integer totalCount = corFacMapper.getCorFacCount(queryPageVo);
        return PageInfoUtil.getResultVoByCorFac(queryPageVo, corFacList, totalCount);
    }

    @Override
    public ResultVo addCorFac(CorFac corFac) {
//        判断编码是否存在
        int codeCount = corFacMapper.getLegalPlantCode(corFac.getLegalPlantCode());
        if (codeCount > 0) {
            return ResultBuildVo.error("编码已存在");
        } else {
//        判断描述是否存在
            int decCount = corFacMapper.getLegalPlantDec(corFac.getLegalPlantDec());
            if (decCount > 0) {
                return ResultBuildVo.error("描述已存在");
            } else {
                //更新数据
                Date date = new Date();
                corFac.setFacAddMan("唐山吴彦祖").setFacAddTime(date).setFacUpdateMan("唐山吴彦祖").setFacUpdateTime(date);
                corFacMapper.addCorFac(corFac);
            }
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateCorFac(CorFac corFac) {
        //根据工厂编码判断是否重复

        Date date = new Date();
        corFac.setFacUpdateMan("唐山吴彦祖").setFacUpdateTime(date);
        corFacMapper.updateCorFac(corFac);

        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateStatus(CorFac corFac) {
        int codeCount = corFacMapper.updateStatus(corFac);
        if (codeCount < 1) {
            return ResultBuildVo.error("该工厂已被使用，不能禁用");
        } else {
            return ResultBuildVo.success();
        }
    }
}
