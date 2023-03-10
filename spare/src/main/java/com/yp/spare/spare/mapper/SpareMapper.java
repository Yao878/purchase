package com.yp.spare.spare.mapper;

import com.yp.spare.spare.bean.Spare;
import com.yp.spare.spare.bean.SpareCor;
import com.yp.spare.spare.bean.SpareCorByName;
import com.yp.spare.utils.QueryPageVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/*
 创建人:唐山吴彦祖
 创建时间:2023/2/14 10:20
 版本号:1.0
 */
@Mapper
public interface SpareMapper {

    List<Spare> queryPage(QueryPageVo queryPageVo);

    Integer queryCount(QueryPageVo queryPageVo);

    int updateStatus(Spare spare);


    int queryCodeCount(String code);

    void addSpare(Spare spare);

    void updateSpare(Spare spare);

    List<Spare> queryPrentId(Spare spare);

    void addSpareCor(SpareCor spareCor);

    List<SpareCorByName> getSparePeronList(@Param("spareIds") List<Integer> spareIds);

    int queryNameCount(@Param("name") String name, @Param("id") Integer id);

    void deleteSpareAndCor(Integer id);

    void addSpareAndCor(@Param("id") Integer id, @Param("legalPersonId") List<Integer> legalPersonId);

    int queryIdBySpare(Integer spareId);
}
