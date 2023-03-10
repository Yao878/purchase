package com.yp.stock.kuwei.service.Impl;

import com.yp.stock.kuwei.bean.Kuwei;
import com.yp.stock.kuwei.bean.KuweiAddVo;
import com.yp.stock.kuwei.bean.KuweiQueryPageVo;
import com.yp.stock.kuwei.bean.PageVo;
import com.yp.stock.kuwei.mapper.KuweiMapper;
import com.yp.stock.kuwei.service.KuweiService;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;


@Service
public class KuweiServiceImpl implements KuweiService {
    @Autowired
    private KuweiMapper kuweiMapper;

    @Override
    public ResultVo queryKuweiPage(KuweiQueryPageVo queryPageVo) {
        //分页展示第一个参数
        queryPageVo.setOffSize((queryPageVo.getPageIndex() - 1) * queryPageVo.getPageSize());
        //分页展示
        List<Kuwei> kuweiList = kuweiMapper.queryKuweiPage(queryPageVo);
        for (Kuwei kuwei : kuweiList) {
            if (kuwei.getStatus()) {
                kuwei.setStatusName("启用");
            }
            kuwei.setStatusName("禁用");
        }
        //查询总条数
        int count = kuweiMapper.getTotalCount(queryPageVo);
        //总页数
        int totalPage = count % queryPageVo.getPageSize() == 0 ? count / queryPageVo.getPageSize() : count / queryPageVo.getPageSize() + 1;

        PageVo<Kuwei> pageVo = new PageVo<>();
        pageVo.setTotalCount(count);//总条数
        pageVo.setPageIndex(queryPageVo.getPageIndex());//当前页数
        pageVo.setPageSize(queryPageVo.getPageSize());//每页展示数量
        pageVo.setTotalPage(totalPage);//总页数
        pageVo.setDataList(kuweiList);
        return ResultBuildVo.success(pageVo);
    }


    @Override
    public ResultVo addKuwei(KuweiAddVo kuweiAddVo) {
        //判断要修改的名字是否重复
        int count = kuweiMapper.yanNameList(kuweiAddVo.getList());
        if (count > 0) {
            return ResultBuildVo.error("要修改的用户名已经存在，请重新修改!");
        }
        //判断要修改的code是否重复
        int count2 = kuweiMapper.yanCodeList(kuweiAddVo.getList());
        if (count2 > 0) {
            return ResultBuildVo.error("要修改的code已经存在，请重新修改!");
        }

        //add
//        List<Kuwei> addList = new ArrayList<>();
//        for (NameAndCode k : kuweiAddVo.getNameAndCodeList()) {
//            Kuwei kuwei = new Kuwei();
//            kuwei.setCode(k.getCode());
//            kuwei.setName(k.getName());
//            kuwei.setStorageSectionCode(kuweiAddVo.getStorageSectionCode());
//            kuwei.setStorageSectionName(kuweiAddVo.getStorageSectionName());
//
//            kuwei.setWareHouseCode(kuweiAddVo.getWareHouseCode());
//            kuwei.setWareHouseName(kuweiAddVo.getWareHouseName());
//            addList.add(kuwei);
//        }
//        System.out.println(addList);
        int count3 = kuweiMapper.addKuweiList(kuweiAddVo.getList());
        if (count3 <= 0) {
            return ResultBuildVo.error("fail to add!");
        }

        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateKuweiStatus(Kuwei kuwei) {

        int num = kuweiMapper.updateKuweiStatus(kuwei);

        if (num < 1) {
            return ResultBuildVo.error("状态修改失败");
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateKuwei(Kuwei kuwei) {
        //判断当前状态
        Integer status = kuweiMapper.getStatus(kuwei.getId());
        if (status == 1) {
            return ResultBuildVo.error("当前状态为启用状态，无法修改!");
        }

        //判断要修改的名字是否重复
        int count = kuweiMapper.getName(kuwei);
        if (count > 0) {
            return ResultBuildVo.error("要修改的用户名已经存在，请重新修改!");
        }

        //修改
        int num = kuweiMapper.updateKuwei(kuwei);
        if (num <= 0) {
            return ResultBuildVo.error("修改失败!");
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo importFile(MultipartFile file) throws Exception {
        //判断名称
        //使用poi读取里面的内容
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        //获取单元格中的信息 at0是获取sheet1中的数据。
        XSSFSheet sheet = workbook.getSheetAt(0);

        ResultVo validata = validata(file, sheet);
        if (validata.getSuccess() == 500) {
            return validata;
        }
        //获取数据
        ResultVo<List<Kuwei>> data = getData(sheet);
        if (data.getSuccess() == 500) {
            return data;
        }
        //验证条数
        System.out.println(data.getData());
        //验证是否为空，长度，数据转换
        ResultVo resultVo = validataRepeatIsNull(data.getData());
        if (resultVo.getSuccess() == 500) {
            return resultVo;
        }
        ResultVo resultVo1 = validataChongFu(data.getData());
        if (resultVo1.getSuccess() == 500) {
            return resultVo1;
        }
        //入库——————————————————————————————
        int i = kuweiMapper.addKuweiList(data.getData());
        if (i > 0) {
            return ResultBuildVo.success(data.getData());
        }
        return ResultBuildVo.error("add fail");
    }

    public ResultVo validataChongFu(List<Kuwei> kuweiList) {
        List<String> codeList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Kuwei k : kuweiList) {
            codeList.add(k.getCode());
            nameList.add(k.getName());
        }

        List<String> codeAllList = new ArrayList<>();//不重复的
        List<String> repeatCodeList = new ArrayList<>();//重复的
        for (String code : codeList) {
            if (codeAllList.contains(code)) {
                repeatCodeList.add(code);
            } else {
                codeAllList.add(code);
            }
        }
        if (!CollectionUtils.isEmpty(repeatCodeList)) {
            return ResultBuildVo.error("存在重复的编码，重复的编码是：" + repeatCodeList);
        }

        List<String> nameAllList = new ArrayList<>();//不重复的
        List<String> repeatNameList = new ArrayList<>();//重复的
        for (String name : nameList) {
            if (nameAllList.contains(name)) {
                repeatNameList.add(name);
            } else {
                nameAllList.add(name);
            }
        }
        if (!CollectionUtils.isEmpty(repeatNameList)) {
            return ResultBuildVo.error("存在重复的name：" + repeatNameList);
        }
        //数据库验证
        List<Kuwei> nameAndCodeList = new ArrayList<>();
        for (Kuwei k : kuweiList) {
            Kuwei n = new Kuwei();
            n.setCode(k.getCode());
            n.setName(k.getName());
            nameAndCodeList.add(n);
        }
        int i1 = kuweiMapper.yanCodeList(nameAndCodeList);
        int i2 = kuweiMapper.yanNameList(nameAndCodeList);
        if (i1 > 0) {
            return ResultBuildVo.error("Code repeat");
        }
        if (i2 > 0) {
            return ResultBuildVo.error("Name repeat");
        }
        return ResultBuildVo.success();
    }


    public ResultVo validataRepeatIsNull(List<Kuwei> kuweiList) {
        /*不为空字段是否为空
         * 5.长度验证
         * 7.数据转换，把汉字转换成数据库的值，状态必须填启用，禁用*/
        StringBuilder stringBuilder = new StringBuilder();
        List<String> errorMessage = new ArrayList<>();
        for (Kuwei k : kuweiList) {
            if (StringUtils.isEmpty(k.getName())) {
                stringBuilder.append("序号" + k.getXuhao() + ":" + "name not null");
                errorMessage.add("");
            } else {
                if (k.getName().length() > 100) {

                    stringBuilder.append("序号" + k.getXuhao() + ":" + ":名称长度过长！");

                }
            }
            if (StringUtils.isEmpty(k.getCode())) {
                return ResultBuildVo.error("序号" + k.getXuhao() + ":编码不能为空！");
            } else {
                if (k.getCode().length() > 100) {
                    return ResultBuildVo.error("序号" + k.getXuhao() + ":编码长度过长！");
                }
            }

            if (k.getStatusName() != "启用" || k.getStatusName() != "禁用") {
                return ResultBuildVo.error("序号" + k.getXuhao() + ":状态只能填写启用或者禁用！");
            } else {
                //三元表达式
                boolean status = k.getStatusName() == "启用" ? true : false;
                k.setStatus(status);
            }
        }
        if (stringBuilder.length() > 0) {
            return ResultBuildVo.error(stringBuilder.toString());
        }
        if (!CollectionUtils.isEmpty(errorMessage)) {
            return ResultBuildVo.error(errorMessage.toString().substring(1, errorMessage.toString().length() - 1));
        }

        return ResultBuildVo.success();
    }


    public ResultVo getData(XSSFSheet sheet) {
        List<Kuwei> kuweiList = new ArrayList<>();
        if (sheet.getLastRowNum() > 10) {
            return ResultBuildVo.error("导入条数不能超过9条，请分批次导入！");
        }
        for (int i = 3; i <= sheet.getLastRowNum(); i++) {
            Kuwei kuwei = new Kuwei();
            String xuhao = sheet.getRow(i).getCell(0).getStringCellValue();
            String code = sheet.getRow(i).getCell(1).getStringCellValue();
            //坑1.格式问题   坑2.读取问题 “null” null ""
            String name = sheet.getRow(i).getCell(2).getStringCellValue();
            String statusName = sheet.getRow(i).getCell(3).getStringCellValue();
            String wareHouseCode = sheet.getRow(i).getCell(4).getStringCellValue();
            String storageSectionCode = sheet.getRow(i).getCell(5).getStringCellValue();

            kuwei.setCode(code);
            kuwei.setXuhao(xuhao);
            kuwei.setName(name);
            kuwei.setStatusName(statusName);
            kuwei.setWareHouseCode(wareHouseCode);
            kuwei.setStorageSectionCode(storageSectionCode);
            kuweiList.add(kuwei);
        }
        return ResultBuildVo.success(kuweiList);
    }

    //验证版本号，和模板是否正确的方法
    public ResultVo validata(MultipartFile file, XSSFSheet sheet) throws Exception {
        //验证版本，最常见的，简单的方式就是判断是否包含。尾追名必须是xlsx结尾
        //string带截取字符串的功能，最后面文件的四个字符是否是xlsx
        if (!file.getOriginalFilename().contains(".xlsx")) {
            return ResultBuildVo.error("文件尾缀名必须是xlsx结尾！");
        }

        XSSFCell cell = sheet.getRow(0).getCell(0);
        System.out.println("cell=========" + cell);
        if (!cell.getStringCellValue().equals("库区明细列表导入")) {
            return ResultBuildVo.error("导入模板错误");
        }
        return ResultBuildVo.success();
    }

}
