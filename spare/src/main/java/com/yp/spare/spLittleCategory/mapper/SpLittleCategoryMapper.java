package com.yp.spare.spLittleCategory.mapper;

import com.yp.spare.spLittleCategory.bean.SpLittleCategory;
import com.yp.spare.spLittleCategory.bean.SubclassExportVo;
import com.yp.spare.utils.QueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/21 20:58
 * 版本号:1.0
 */
@Mapper
public interface SpLittleCategoryMapper {

    int getSpLittleCategoryByCode(String categoryCode);

    int getSpLittleCategoryByName(String categoryName);

    int addSpLittleCategory(List<SpLittleCategory> spLittleCategory);

    List<SpLittleCategory> selectAll();

    int getSpLittleCategoryByNameAndId(@Param("categoryName") String categoryName, @Param("categoryNumber") Integer categoryNumber);

    void updateSpLittleCategory(SpLittleCategory spLittleCategory);

    SpLittleCategory getSpLittleCategoryById(Integer categoryNumber);

    int updateSpLittleCategoryByEnable(SpLittleCategory spLittleCategory);

    Integer queryCount(QueryPageVo queryPageVo);

    List<SpLittleCategory> queryPage(QueryPageVo queryPageVo);

    List<SpLittleCategory> queryAll();

    int queryLittleCount(@Param("subClassId") Integer subClassId);

    int saveBatch(List<SpLittleCategory> littleCategoryList);

    List<String> selectCodeList(List<String> codeCountList);

    List<String> selectNameList(List<String> nameCountList);

    List<SpLittleCategory> exportFile(SubclassExportVo subclassExportVo);
}


