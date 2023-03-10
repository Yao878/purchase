package com.yp.spare.spBroadCategory.mapper;/*
 创建人:唐山吴彦祖
 创建时间:2023/1/27 14:49
 版本号:1.0
 */

import com.yp.spare.spBroadCategory.bean.SpBroadCategory;
import com.yp.spare.utils.QueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpBroadCategoryMapper {

    int getSpBroadCategoryByCode(String categoryCode);

    int getSpBroadCategoryByName(String categoryName);

    int addSpBroadCategory(SpBroadCategory spBroadCategory);

    Object getSpBroadCategoryByPage(QueryPageVo queryPageVo);

    List<SpBroadCategory> queryPage(QueryPageVo queryPageVo);

    Integer queryPageCount(QueryPageVo queryPageVo);

    SpBroadCategory getSpBroadCategoryById(Integer id);

    int getSpBroadCategoryByNameAndId(@Param("name") String name, @Param("id") Integer id);

    void updateSpLittleCategory(SpBroadCategory spBroadCategory);

    int updateSpBroadCategoryByEnable(SpBroadCategory spBroadCategory);

    List<SpBroadCategory> queryAll();

    int queryBroadCount(@Param("planClassId") Integer planClassId);
}
