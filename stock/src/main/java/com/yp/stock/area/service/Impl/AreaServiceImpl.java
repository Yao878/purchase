package com.yp.stock.area.service.Impl;

import com.yp.stock.area.bean.Area;
import com.yp.stock.area.bean.AreaAttribute;
import com.yp.stock.area.bean.AreaAttributeQueryPageVo;
import com.yp.stock.area.bean.AreaQueryPageVo;
import com.yp.stock.area.mapper.AreaMapper;
import com.yp.stock.area.service.AreaService;
import com.yp.stock.utils.AllRecordsVo;
import com.yp.stock.utils.ParamUtil;
import com.yp.stock.utils.ResultBuildVo;
import com.yp.stock.utils.ResultVo;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaMapper areaMapper;

    @Override
    public ResultVo addArea(List<Area> areaList) {
        //检查是否存在重复
        int check = areaMapper.checkAreaCode(areaList);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaCode");
        }
        check = areaMapper.checkAreaName(areaList);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaName");
        }
        //设置基本信息
        Date d = new Date();
        for (Area a : areaList) {
            a.setAddTime(d).setAddMan("admin").setUpdateTime(d).setUpdateMan("admin");
        }
        //入库
        areaMapper.addAreaByList(areaList);
        for (Area a : areaList) {
            if (!a.getAttributeList().isEmpty()) {
                areaMapper.addRelationship(a.getAttributeList(), a.getAreaCode());
            } else {
                return ResultBuildVo.success();
            }
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateAreaStatus(Area area) {
        int check = areaMapper.updateAreaStatus(area);
        if (check == 1) {
            return ResultBuildVo.success();
        }
        return ResultBuildVo.error("updateStatus Failed");
    }

    @Override
    public ResultVo updateArea(Area area) {
        List<Area> areaList = new ArrayList<>();
        areaList.add(area);
        //验证重复
        int check = areaMapper.checkAreaName(areaList);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaName");
        }
        check = areaMapper.updateArea(area);
        areaMapper.deleteRelationship(area);
        areaMapper.addRelationship(area.getAttributeList(), area.getAreaCode());
        if (check == 1) {
            return ResultBuildVo.success();
        }
        return ResultBuildVo.error("updateStatus Failed");
    }

    @Override
    public ResultVo queryAreaPage(AreaQueryPageVo queryPageVo) {
        //计算分页用的参数值
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        //获取数据
        List<Area> areaList = areaMapper.queryAreaPage(queryPageVo);
        for (Area area : areaList) {
            area.setAttributeList(areaMapper.queryAttribute(area.getAreaCode()));
            area.setStatusName(area.getStatus() ? "启用" : "禁用");
        }

        //查询总条数
        Integer totalCount = areaMapper.queryAreaCount(queryPageVo);
        //计算
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(ParamUtil.PAGE_COUNT);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % ParamUtil.PAGE_COUNT == 0 ? totalCount / ParamUtil.PAGE_COUNT : totalCount / ParamUtil.PAGE_COUNT + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(areaList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);

    }

    @Override
    public ResultVo addAreaAttribute(AreaAttribute areaAttribute) {
        //检查是否存在重复
        int check = areaMapper.checkAreaAttributeCode(areaAttribute);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaAttributeCode");
        }
        check = areaMapper.checkAreaAttributeName(areaAttribute);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaAttributeName");
        }
        //设置基本信息
        Date d = new Date();
        areaAttribute.setAddTime(d).setAddMan("admin").setUpdateTime(d).setUpdateMan("admin");

        //入库
        Integer flag = areaMapper.addAreaAttributeByVo(areaAttribute);
        if (flag > 0) {
            return ResultBuildVo.success(flag);
        } else {
            return ResultBuildVo.error("addAreaAttribute Failed");
        }
    }

    @Override
    public ResultVo updateAreaAttributeStatus(AreaAttribute areaAttribute) {
        int check = areaMapper.updateAreaAttributeStatus(areaAttribute);
        if (check == 1) {
            return ResultBuildVo.success();
        }
        return ResultBuildVo.error("updateStatus Failed");
    }

    @Override
    public ResultVo updateAreaAttribute(AreaAttribute areaAttribute) {
        //验证重复
        int check = areaMapper.checkAreaAttributeName(areaAttribute);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaName");
        }
        check = areaMapper.updateAreaAttribute(areaAttribute);
        if (check == 1) {
            return ResultBuildVo.success();
        }
        return ResultBuildVo.error("updateStatus Failed");
    }

    @Override
    public ResultVo queryAreaAttributePage(AreaAttributeQueryPageVo queryPageVo) {
        //计算分页用的参数值
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        List<AreaAttribute> areaAttributeList = areaMapper.queryAreaAttributePage(queryPageVo);
        for (AreaAttribute a : areaAttributeList) {
            a.setStatusName(a.getStatus() ? "启用" : "禁用");
        }
        //查询总条数
        Integer totalCount = areaMapper.queryAreaAttributeCount(queryPageVo);
        //计算
        AllRecordsVo allRecordsVo = new AllRecordsVo();
        allRecordsVo.setCurrentPage(queryPageVo.getPageIndex());
        allRecordsVo.setPageSize(ParamUtil.PAGE_COUNT);
        allRecordsVo.setTotalSize(totalCount);
        Integer totalPage = totalCount % ParamUtil.PAGE_COUNT == 0 ? totalCount / ParamUtil.PAGE_COUNT : totalCount / ParamUtil.PAGE_COUNT + 1;
        allRecordsVo.setTotalPage(totalPage);
        allRecordsVo.setDataList(areaAttributeList);
        //赋值
        return ResultBuildVo.success(allRecordsVo);
    }

    //导入
    @Override
    public ResultVo importFile(MultipartFile file) throws Exception {
        //使用poi读取里面的内容
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
        //获取单元格中的信息 at0是获取sheet1中的数据。
        XSSFSheet sheet = workbook.getSheetAt(0);
        //错误信息记录
        List<String> erroLog = new ArrayList<>();

        //验证格式
        ResultVo validata = validata(file, sheet);
        if (validata.getSuccess() == 500) {
            return validata;
        }
        //读取全部数据
        ResultVo<List<Area>> data = getData(sheet);
        if (data.getSuccess() == 500) {
            return data;
        }
        //验证非空，长度，数据转换
        ResultVo checkPart1 = checkDataNotNullAndOverLenThenTrans(data.getData(), erroLog);
//        if (!checkPart1.getSuccess()){
//            return checkPart1;
//        }
        //验证重复
        ResultVo checkPart2 = checkDataNoRepeat(data.getData(), erroLog);
//        if (!checkPart2.getSuccess()){
//            return checkPart2;
//        }
        if (!erroLog.isEmpty()) {
            return ResultBuildVo.error(erroLog.toString());
        }
        //数据库验重，入库
        ResultVo checkPart3 = addToDataBase(data.getData());
        if (checkPart3.getSuccess() == 500) {
            return checkPart3;
        }

        return ResultBuildVo.success(data.getData());

    }

    public ResultVo validata(MultipartFile file, XSSFSheet sheet) throws Exception {
        //验证版本，最常见的，简单的方式就是判断是否包含。尾追名必须是xlsx结尾
        //string带截取字符串的功能，最后面文件的四个字符是否是xlsx
        if (!file.getOriginalFilename().contains(".xlsx")) {
            return ResultBuildVo.error("文件尾缀名必须是xlsx结尾！");
        }

        XSSFCell cell = sheet.getRow(0).getCell(0);
        if (!cell.getStringCellValue().equals("库区导入模版")) {
            return ResultBuildVo.error("导入模板错误");
        }
        return ResultBuildVo.success();
    }

    public ResultVo getData(XSSFSheet sheet) {
        List<Area> areaList = new ArrayList<>();
        for (int i = 2; i <= sheet.getLastRowNum(); i++) {
            Area area = new Area();
            //测试是否为空，为空则设定为空值
            for (int c = 0; c < 9; c++) {
                if (sheet.getRow(i).getCell(c) == null) {
                    sheet.getRow(i).createCell(c).setCellValue("");
                }
            }
            String xuhao = sheet.getRow(i).getCell(0).getStringCellValue();
            String code = sheet.getRow(i).getCell(1).getStringCellValue();
            String name = sheet.getRow(i).getCell(2).getStringCellValue();
            String status = sheet.getRow(i).getCell(3).getStringCellValue();
            String remark = sheet.getRow(i).getCell(4).getStringCellValue();
            String storecode = sheet.getRow(i).getCell(5).getStringCellValue();
            String management = sheet.getRow(i).getCell(6).getStringCellValue();
            String wms = sheet.getRow(i).getCell(7).getStringCellValue();
            String vip = sheet.getRow(i).getCell(8).getStringCellValue();
            area.setId(Integer.parseInt(xuhao));
            area.setAreaCode(code);
            area.setName(name);
            area.setStatusName(status);
            area.setRemark(remark);
            area.setWareHouseCode(storecode);
            area.setManagement(Integer.parseInt(management));
            area.setWms(Integer.parseInt(wms));
            area.setVip(Integer.parseInt(vip));
            areaList.add(area);
        }
        return ResultBuildVo.success(areaList);
    }

    public ResultVo checkDataNotNullAndOverLenThenTrans(List<Area> areaList, List<String> erroLog) {
        /*不为空字段是否为空
         * 5.长度验证
         * 7.数据转换，把汉字转换成数据库的值，状态必须填启用，禁用*/
        for (Area area : areaList) {
            if (area.getName().equals("")) {
                erroLog.add("序号" + area.getId() + ":" + "名称不能为空");
//                return ResultBuildVo.error("序号"+subClassVo.getXuhao()+":"+"名称不能为空");
            } else {
                if (area.getName().length() > 100) {
                    erroLog.add("序号" + area.getId() + ":名称长度过长！");
//                    return ResultBuildVo.error("序号"+subClassVo.getXuhao()+":名称长度过长！");
                }
            }
            if (area.getAreaCode().equals("")) {
                erroLog.add("序号" + area.getId() + ":编码不能为空！");
//                return ResultBuildVo.error("序号"+subClassVo.getXuhao()+":编码不能为空！");
            } else {
                if (area.getAreaCode().length() > 100) {
                    erroLog.add("序号" + area.getId() + ":编码长度过长！");
//                    return ResultBuildVo.error("序号"+subClassVo.getXuhao()+":编码长度过长！");
                }
            }
            if (area.getRemark().length() > 300) {
                erroLog.add("序号" + area.getId() + ":描述长度过长！");
//                return ResultBuildVo.error("序号"+subClassVo.getXuhao()+":描述长度过长！");
            }

            if (area.getStatusName().equals("启用")) {
                area.setStatus(true);
            } else if (area.getStatusName().equals("禁用")) {
                area.setStatus(false);
            } else {
                erroLog.add("序号" + area.getId() + ":状态只能填写启用或者禁用！");
            }

        }
        return ResultBuildVo.success();
    }

    public ResultVo checkDataNoRepeat(List<Area> areaList, List<String> erroLog) {
        List<String> codeList = new ArrayList<>();
        List<String> nameList = new ArrayList<>();
        for (Area area : areaList) {
            codeList.add(area.getAreaCode());
            nameList.add(area.getName());
        }
        //stream
        List<String> codeAllList = new ArrayList<>();//不重复的
        List<String> repeatCodeList = new ArrayList<>();//重复的
        for (String code : codeList) {
            if (codeAllList.contains(code)) {
                repeatCodeList.add(code);
            } else {
                codeAllList.add(code);
            }
        }
        if (!repeatCodeList.isEmpty()) {
            erroLog.add("存在重复的编码，重复的编码是：" + repeatCodeList);
//            return ResultBuildVo.error("存在重复的编码，重复的编码是："+repeatCodeList);
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
        if (!repeatNameList.isEmpty()) {
            erroLog.add("存在重复的名称，重复的名称是：" + repeatCodeList);
//            return ResultBuildVo.error("存在重复的名称，重复的名称是："+repeatCodeList);
        }

        return ResultBuildVo.success();
    }

    public ResultVo addToDataBase(List<Area> areaList) {
        //检查是否存在重复
        int check = areaMapper.checkAreaCode(areaList);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaCode");
        }
        check = areaMapper.checkAreaName(areaList);
        if (check > 0) {
            return ResultBuildVo.error("RepeatAreaName");
        }
        //设置基本信息
        Date d = new Date();
        for (Area a : areaList) {
            a.setAddTime(d).setAddMan("admin").setUpdateTime(d).setUpdateMan("admin");
        }
        //入库
        areaMapper.addAreaByList(areaList);
        return ResultBuildVo.success();
    }
}
