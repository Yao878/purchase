package com.yp.stock.area.mapper;

import com.yp.stock.area.bean.Area;
import com.yp.stock.area.bean.AreaAttribute;
import com.yp.stock.area.bean.AreaAttributeQueryPageVo;
import com.yp.stock.area.bean.AreaQueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AreaMapper {

    //库区
    int checkAreaCode(List<Area> areaList);

    int checkAreaName(List<Area> areaList);

    void addAreaByList(List<Area> areaList);

    int updateAreaStatus(Area area);

    int updateArea(Area area);

    List<Area> queryAreaPage(AreaQueryPageVo queryPageVo);

    int queryAreaCount(AreaQueryPageVo queryPageVo);

    //操作关系表
    void addRelationship(@Param("attributeList") List<String> attributeList, @Param("areaCode") String areaCode);

    void deleteRelationship(Area area);

    List<String> queryAttribute(@Param("areaCode") String areaCode);

    //库区属性
    int checkAreaAttributeCode(AreaAttribute areaAttribute);

    int checkAreaAttributeName(AreaAttribute areaAttribute);

    Integer addAreaAttributeByVo(AreaAttribute areaAttribute);

    int updateAreaAttributeStatus(AreaAttribute areaAttribute);

    int updateAreaAttribute(AreaAttribute areaAttribute);

    List<AreaAttribute> queryAreaAttributePage(AreaAttributeQueryPageVo queryPageVo);

    int queryAreaAttributeCount(AreaAttributeQueryPageVo queryPageVo);

    List<Area> getDown();

    List<Area> getKuquByKufang(int id);

}
