package com.yp.spare.corfac.mapper;

import com.yp.spare.corfac.bean.CorFac;
import com.yp.spare.corfac.bean.CorFacByList;
import com.yp.spare.corfac.bean.CorNameVo;
import com.yp.spare.utils.QueryPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


/*
 创建人:唐山吴彦祖
 创建时间:2023/2/10 20:32
 版本号:1.0
 */
@Mapper
public interface CorFacMapper {

    List<CorFac> getCorFacByPage(QueryPageVo queryPageVo);

    Integer getCorFacCount(QueryPageVo queryPageVo);

    int getLegalPlantCode(String legalPlantCode);

    int getLegalPlantDec(String legalPlantDec);

    void addCorFac(CorFac corFac);

    List<CorNameVo> getDown(Integer userId);

    void updateCorFac(CorFac corFac);

    int updateStatus(CorFac corFac);

    List<CorFacByList> queryPlantListByLegalPersonCode(String i, String i1);
}
