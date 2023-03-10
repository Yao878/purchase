package com.yp.stock.area.service;

import com.yp.stock.area.bean.Area;
import com.yp.stock.area.bean.AreaAttribute;
import com.yp.stock.area.bean.AreaAttributeQueryPageVo;
import com.yp.stock.area.bean.AreaQueryPageVo;
import com.yp.stock.utils.ResultVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AreaService {

    ResultVo addArea(List<Area> areaList);

    ResultVo updateAreaStatus(Area area);

    ResultVo updateArea(Area area);

    ResultVo queryAreaPage(AreaQueryPageVo queryPageVo);

    ResultVo addAreaAttribute(AreaAttribute areaAttribute);

    ResultVo updateAreaAttributeStatus(AreaAttribute areaAttribute);

    ResultVo updateAreaAttribute(AreaAttribute areaAttribute);

    ResultVo queryAreaAttributePage(AreaAttributeQueryPageVo queryPageVo);

    ResultVo importFile(MultipartFile file) throws Exception;


}
