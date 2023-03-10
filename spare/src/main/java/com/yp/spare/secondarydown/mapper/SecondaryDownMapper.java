package com.yp.spare.secondarydown.mapper;

import com.yp.spare.secondarydown.bean.SecondaryDown;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/28 14:49
 * 版本号:1.0
 */
@Mapper
public interface SecondaryDownMapper {

    List<SecondaryDown> selectAll();

    int addParent(SecondaryDown secondaryDown);

    int addSon(SecondaryDown secondaryDown);

    List<SecondaryDown> selectSon(String dictionaryCode, Boolean status);

    void disableParent(SecondaryDown secondaryDown);

    void disableByParentCode(String parentCode);

    void selectByDictionaryCode(String dictionaryCode);

    void enableByParentCode(String parentCode);

    void enableParent(SecondaryDown secondaryDown);

    void enable(SecondaryDown secondaryDown);

    void enableSon(SecondaryDown secondaryDown);

    SecondaryDown selectByCode(String dictionaryCode);

    void enableBySon(SecondaryDown secondaryDown);

    void disableBySelf(SecondaryDown secondaryDown);

    void disableParentCode(SecondaryDown secondaryDown);
}
