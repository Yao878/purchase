package com.yp.spare.threshold.mapper;

import com.yp.spare.threshold.bean.Threshold;
import com.yp.spare.utils.QueryPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/20 17:31
 版本号:1.0
 */
@Mapper
public interface ThresholdMapper {

    List<Threshold> getThresholdByPage(QueryPageVo queryPageVo);

    Integer getThresholdCount(QueryPageVo queryPageVo);

    Integer addThreshold(Threshold threshold);

    Integer updateThreshold(Threshold threshold);

    int updateStatus(Threshold threshold);
}
