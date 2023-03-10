package com.yp.spare.secondarydown.serviceImpl;

import com.yp.spare.secondarydown.bean.SecondaryDown;
import com.yp.spare.secondarydown.mapper.SecondaryDownMapper;
import com.yp.spare.secondarydown.service.SecondaryDownService;
import com.yp.spare.utils.ResultBuildVo;
import com.yp.spare.utils.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/3/1 10:22
 * 版本号:1.0
 */
@Service
public class SecondaryDownServiceImpl implements SecondaryDownService {
    @Autowired
    private SecondaryDownMapper secondaryDownMapper;

    @Override
    public ResultVo selectAll() {
        //右侧菜单栏查询全部
        List<SecondaryDown> lists = secondaryDownMapper.selectAll();
        //所有的子集
        List<SecondaryDown> sonLists = new ArrayList<>();
        //所有的父集
        List<SecondaryDown> parentLists = new ArrayList<>();
        for (SecondaryDown list : lists) {
            if (list.getParentCode() == null) {
                //如果是父类，那么就把数据放到父集中
                parentLists.add(list);
            } else {
                //如果是子类，那么就把数据放到子集中
                sonLists.add(list);
            }
        }
        //遍历父集，把子集放到父集中
        for (SecondaryDown parentList : parentLists) {
            //子集
            List<SecondaryDown> sonList = new ArrayList<>();
            for (SecondaryDown sonList1 : sonLists) {
                if (parentList.getDictionaryCode().equals(sonList1.getParentCode())) {
                    sonList.add(sonList1);
                }
            }
            parentList.setSecondarySon(sonList);
        }
        return ResultBuildVo.success(parentLists);
    }

    @Override
    public ResultVo addParent(SecondaryDown secondaryDown) {
        //查询出所有的数据
        List<SecondaryDown> lists = secondaryDownMapper.selectAll();
        for (SecondaryDown list : lists) {
            //如果有相同编码的数据，那么就不添加
            if (list.getDictionaryCode().equals(secondaryDown.getDictionaryCode())) {
                return ResultBuildVo.error("编码重复");
            }
        }
        //如果字典编码为空，那么就不添加
        if (secondaryDown.getDictionaryCode() == null) {
            return ResultBuildVo.error("编码不能为空");
        }
        return ResultBuildVo.success(secondaryDownMapper.addParent(secondaryDown));
    }

    @Override
    public ResultVo addSon(SecondaryDown secondaryDown) {
        //查询出所有的数据
        List<SecondaryDown> lists = secondaryDownMapper.selectAll();
        for (SecondaryDown list : lists) {
            //如果有相同编码的数据，那么就不添加
            if (list.getDictionaryCode().equals(secondaryDown.getDictionaryCode())) {
                return ResultBuildVo.error("编码重复");
            }
        }
        //如果字典编码为空，那么就不添加
        if (secondaryDown.getDictionaryCode() == null) {
            return ResultBuildVo.error("编码不能为空");
        }
        return ResultBuildVo.success(secondaryDownMapper.addSon(secondaryDown));
    }

    @Override
    public ResultVo disable(SecondaryDown secondaryDown) {
        secondaryDownMapper.selectByDictionaryCode(secondaryDown.getDictionaryCode());
        //如果禁用的是父类，那么就把父类下的子类也禁用
        if (secondaryDown.getParentCode() == null) {
            secondaryDownMapper.disableParent(secondaryDown);
        } else {
            //如果禁用的是子类，判断该子类的父类下是否还有其他子类启用，如果没有，那么就把父类也禁用
            List<SecondaryDown> list = secondaryDownMapper.selectSon(secondaryDown.getParentCode(), secondaryDown.getStatus());
            if (list.size() == 1) {
                secondaryDownMapper.disableParentCode(secondaryDown);
            }

        }
        //不管禁用的是父类还是子类，都要把自己禁用
        secondaryDownMapper.disableBySelf(secondaryDown);
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo enable(SecondaryDown secondaryDown) {
        //如果启用的是父类，那么就把父类下的子类全部启用
        if (secondaryDown.getParentCode() == null) {
            //先启用子类
            secondaryDownMapper.enableParent(secondaryDown);
            //并且启用父类
            secondaryDownMapper.enable(secondaryDown);
        } else {
            //如果启用的是子类，那么父类必须启用
            //先启用父类
            //查出当前子类的父类
            secondaryDownMapper.enableBySon(secondaryDown);
            //再启用子类
            secondaryDownMapper.enableSon(secondaryDown);
        }
        return ResultBuildVo.success();
    }

    @Override
    public Object selectByCode(SecondaryDown secondaryDown) {
        //按照多个条件查询，结果放到map里
        Map<String, Object> map = new HashMap<>();
        //查询出所有的数据
        List<SecondaryDown> lists = secondaryDownMapper.selectAll();
        //遍历所有的数据，把父类和子类分开
        //父类
        List<SecondaryDown> parentLists = new ArrayList<>();
        //子类
        List<SecondaryDown> sonLists = new ArrayList<>();
        for (SecondaryDown list : lists) {
            if (list.getParentCode() == null) {
                parentLists.add(list);
            } else {
                sonLists.add(list);
            }
        }
        //遍历父类，把父类下的子类放到父类里
        for (SecondaryDown parentList : parentLists) {
            List<SecondaryDown> sonList = new ArrayList<>();
            for (SecondaryDown sonList1 : sonLists) {
                if (parentList.getDictionaryCode().equals(sonList1.getParentCode())) {
                    sonList.add(sonList1);
                }
            }
            parentList.setSecondarySon(sonList);
        }
        //把父类放到map里
        map.put("parent", parentLists);
        //把子类放到map里
        map.put("son", sonLists);
        return map;
    }
}



