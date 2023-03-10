package com.yp.stock.kuwei.service;

import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.kuwei.bean.KuweiAddVo;
import com.yp.stock.kuwei.bean.KuweiQueryPageVo;
import com.yp.stock.utils.ResultVo;
import org.springframework.web.multipart.MultipartFile;


public interface KuweiService {
    ResultVo queryKuweiPage(KuweiQueryPageVo queryPageVo);

    ResultVo addKuwei(KuweiAddVo kuweiAddVo);

    ResultVo updateKuwei(Kuwei kuwei);

    ResultVo importFile(MultipartFile file) throws Exception;

    ResultVo updateKuweiStatus(Kuwei kuwei);
}
