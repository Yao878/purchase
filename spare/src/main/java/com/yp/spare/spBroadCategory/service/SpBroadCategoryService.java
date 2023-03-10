package com.yp.spare.spBroadCategory.service;/*
 创建人:唐山吴彦祖
 创建时间:2023/1/27 14:46
 版本号:1.0
 */

import com.yp.spare.spBroadCategory.bean.SpBroadCategory;
import com.yp.spare.utils.QueryPageVo;
import com.yp.spare.utils.ResultVo;

public interface SpBroadCategoryService {

    ResultVo addSpBroadCategory(SpBroadCategory spBroadCategory);

    ResultVo updateSpBroadCategory(SpBroadCategory spBroadCategory);

    ResultVo getSpBroadCategoryByPage(QueryPageVo queryPageVo);

    Object updateSpBroadCategoryByEnable(SpBroadCategory spBroadCategory);

    ResultVo queryAll();
}
