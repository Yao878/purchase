package com.yp.stock.kuwei.mapper;

import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.kuwei.bean.KuweiQueryPageVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface KuweiMapper {

    Integer getTotalCount(KuweiQueryPageVo queryPageVo);

    List<Kuwei> queryKuweiPage(KuweiQueryPageVo queryPageVo);

    Integer getStatus(Integer id);

    int updateKuwei(Kuwei kuwei);

    int addKuweiList(List<Kuwei> addList);

    int yanNameList(List<Kuwei> nameAndCodeList);

    int yanCodeList(List<Kuwei> nameAndCodeList);

    int getName(Kuwei kuwei);

    int updateKuweiStatus(Kuwei kuwei);

    List<Kuwei> getKuweiByKuqu(int storageSectionId);
}
