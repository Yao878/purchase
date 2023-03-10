package com.yp.spare.secondarydown.service;

import com.yp.spare.secondarydown.bean.SecondaryDown;
import com.yp.spare.utils.ResultVo;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/1 10:22
 * 版本号:1.0
 */

public interface SecondaryDownService {
    ResultVo selectAll();

    ResultVo addParent(SecondaryDown secondaryDown);

    ResultVo addSon(SecondaryDown secondaryDown);

    ResultVo disable(SecondaryDown secondaryDown);

    ResultVo enable(SecondaryDown secondaryDown);

    Object selectByCode(SecondaryDown secondaryDown);
}
