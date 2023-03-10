package com.yp.spare.spareCostPrice.service;

import com.yp.spare.spareCostPrice.bean.QueryPageVo;
import com.yp.spare.spareCostPrice.bean.SpareCostPrice;
import com.yp.spare.spareCostPrice.bean.importSpare;
import com.yp.spare.utils.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/22 14:29
 * 版本号:1.0
 */
public interface SpareCostPriceService {
    ResultVo queryPage(QueryPageVo queryPageVo);

    ResultVo addSpareCostPrice(List<SpareCostPrice> spareCostPrice);

    Object updateSpareCostPrice(SpareCostPrice spareCostPrice);

    Object updateStatus(SpareCostPrice spareCostPrice);

    ResultVo poiFile(MultipartFile file, importSpare spareCondition) throws Exception;

    ResultVo exportFile(SpareCostPrice subclassExportVo, HttpServletResponse response) throws Exception;
}
