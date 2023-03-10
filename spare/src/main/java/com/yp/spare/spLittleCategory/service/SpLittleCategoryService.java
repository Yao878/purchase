package com.yp.spare.spLittleCategory.service;

import com.yp.spare.spLittleCategory.bean.SpLittleCategory;
import com.yp.spare.spLittleCategory.bean.SubclassExportVo;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/*
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/21 20:55
 * 版本号:1.0
 */

public interface SpLittleCategoryService {

    ResultVo addSpLittleCategory(SpLittleCategory spLittleCategory);

    ResultVo updateSpLittleCategory(SpLittleCategory spLittleCategory);


    ResultVo updateSpLittleCategoryByEnable(SpLittleCategory spLittleCategory);

    ResultVo getSpLittleCategoryByPage(QueryPageVo queryPageVo);

    ResultVo queryAll();

    ResultVo poiFile(MultipartFile file) throws Exception;

    ResultVo exportFile(SubclassExportVo subclassExportVo, HttpServletResponse response) throws Exception;
}
