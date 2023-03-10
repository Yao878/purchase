package com.yp.spare.spLittleCategory.impl;

import com.yp.spare.spLittleCategory.bean.SpLittleCategory;
import com.yp.spare.spLittleCategory.bean.SubclassExportVo;
import com.yp.spare.spLittleCategory.mapper.SpLittleCategoryMapper;
import com.yp.spare.spLittleCategory.service.SpLittleCategoryService;
import com.yp.spare.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/*
 * 创建人:唐山吴彦祖
 * 创建时间:2023/1/21 20:56
 * 版本号:1.0
 */
@Service
public class SpLittleCategoryServiceImpl implements SpLittleCategoryService {
    @Autowired
    private SpLittleCategoryMapper spLittleCategoryMapper;

    @Nullable
    private static ResultVo remarkResultVo(XSSFSheet sheet, int i, SpLittleCategory littleCategory, StringBuilder errorMessageList) {

        XSSFCell remarkCell = sheet.getRow(i).getCell(4);
        if (remarkCell != null) {
            String remark = remarkCell.getStringCellValue();
            if (StringUtils.isNotEmpty(remark) && remark.length() > 200) {//如果备注不为空且不大于200          为空的话直接跳过备注
                errorMessageList.append("第").append(i + 1).append("行的备注长度不能超过200！！");
            }
            littleCategory.setRemark(remark);
        }
        return null;
    }

    @Nullable
    private static ResultVo statusResultVo(XSSFSheet sheet, int i, SpLittleCategory littleCategory, StringBuilder errorMessageList) {
        XSSFCell statusNameCell = sheet.getRow(i).getCell(3);
        if (statusNameCell == null) {
            errorMessageList.append("第").append(i + 1).append("行的状态不能为空！！");
        } else {
            String statusName = statusNameCell.getStringCellValue();
            if (StringUtils.isEmpty(statusName)) {
                errorMessageList.append("第").append(i + 1).append("行的状态不能为空！！");
            } else if (statusName.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的状态长度不能超过200！！");
            } else if (!statusName.equals("启用") && !statusName.equals("禁用")) {
                errorMessageList.append("第").append(i + 1).append("行的状态名称不正确！！");
            }
            littleCategory.setStatus(statusName.equals("启用"));//5.数据转换 状态名称转换为状态值
        }
        return null;
    }

    @Nullable
    private static ResultVo codeResultVo(XSSFSheet sheet, List<String> codeCountList, int i, SpLittleCategory littleCategory, StringBuilder errorMessageList) {
        XSSFCell codeCell = sheet.getRow(i).getCell(2);
        if (codeCell == null) {
            errorMessageList.append("第").append(i + 1).append("行的编码不能为空！！");
        } else {
            String code = codeCell.getStringCellValue();
            if (StringUtils.isEmpty(code)) {
                errorMessageList.append("第").append(i + 1).append("行的编码不能为空！！");
            } else if (code.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的编码长度不能超过200！！");
            } else {
                littleCategory.setCode(code);
                codeCountList.add(code);
            }
        }
        return null;
    }

    @Nullable
    private static ResultVo nameResultVo(XSSFSheet sheet, List<String> nameCountList, int i, SpLittleCategory littleCategory, StringBuilder errorMessageList) {
        XSSFCell nameCell = sheet.getRow(i).getCell(1);
        if (nameCell == null) {//避免了空字符串传进来为空指针的异常
            errorMessageList.append("第").append(i + 1).append("行的名称不能为空！！");
        } else {
            String name = nameCell.getStringCellValue();
            if (StringUtils.isEmpty(name)) {
                errorMessageList.append("第").append(i + 1).append("行的名称不能为空！！");
            } else if (name.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的名称长度不能超过200！！");
            } else {
                littleCategory.setName(name);
                nameCountList.add(name);
            }
        }
        return null;
    }

    @Override
    public ResultVo addSpLittleCategory(SpLittleCategory spLittleCategory) {
        List<SpLittleCategory> spLittleCategoryList = new ArrayList<>();
        spLittleCategoryList.add(spLittleCategory);
        //2.判断编码是否重复
        List<String> nameCountList = new ArrayList<>();
        List<String> codeCountList = new ArrayList<>();
        for (SpLittleCategory spLittleCategory1 : spLittleCategoryList) {
            nameCountList.add(spLittleCategory1.getName());
            codeCountList.add(spLittleCategory1.getCode());
        }
        //2.1.根据名称查询
        List<String> nameList = spLittleCategoryMapper.selectNameList(nameCountList);
        if (!(nameList.isEmpty())) {
            return ResultBuildVo.error("备件名称已经存在，请重新添加！" + nameList);
        }
        //2.1.根据名称查询
        List<String> codeList = spLittleCategoryMapper.selectCodeList(codeCountList);
        if (!(codeList.isEmpty())) {
            return ResultBuildVo.error("备件编码已经存在，请重新添加！" + codeList);
        }
        return ResultBuildVo.success(spLittleCategoryMapper.saveBatch(spLittleCategoryList));
    }

    @Override
    public ResultVo updateSpLittleCategory(SpLittleCategory spLittleCategory) {
        //根据主键 查询当前状态
        SpLittleCategory Vo = spLittleCategoryMapper.getSpLittleCategoryById(spLittleCategory.getId());
        //如果为启用状态，不允许修改 直接返回错误信息
        if (Vo.getStatus()) {
            return ResultBuildVo.error("当前状态为启用，不允许修改！");
        } else {
            //如果为禁用状态，允许修改 直接修改
            //根据名称判断是否重复
            int count = spLittleCategoryMapper.getSpLittleCategoryByNameAndId(spLittleCategory.getName(), spLittleCategory.getId());
            if (count > 0) {
                return ResultBuildVo.error("当前数据名称重复，请重新修改！");
            } else {
                //修改数据
                spLittleCategory.setCategoryUpdateMan("admin");
                spLittleCategoryMapper.updateSpLittleCategory(spLittleCategory);
            }
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo updateSpLittleCategoryByEnable(SpLittleCategory spLittleCategory) {
        int count = spLittleCategoryMapper.updateSpLittleCategoryByEnable(spLittleCategory);
        if (count < 1) {
            return ResultBuildVo.error("状态修改失败！");
        } else {
            return ResultBuildVo.success();
        }
    }

    @Override
    public ResultVo getSpLittleCategoryByPage(QueryPageVo queryPageVo) {
        //计算分页用的参数值
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        //查询出的数据
        List<SpLittleCategory> subclassList = spLittleCategoryMapper.queryPage(queryPageVo);
        for (SpLittleCategory sub : subclassList) {
            if (sub.getStatus()) {
                sub.setStatusName("启用");
            } else {
                sub.setStatusName("禁用");
            }
        }
        //查询总条数
        Integer totalCount = spLittleCategoryMapper.queryCount(queryPageVo);
        //计算
        return PageInfoUtil.getResultVoByLittle(queryPageVo, subclassList, totalCount);
    }

    @Override
    public ResultVo queryAll() {
        List<SpLittleCategory> littleCategoryList = spLittleCategoryMapper.queryAll();
        return ResultBuildVo.success(littleCategoryList);
    }

    @Override
    public ResultVo poiFile(MultipartFile file) throws Exception {
        StringBuilder errorMessageList = new StringBuilder();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());//2.获取工作簿的开头 查看是否满足要求
        XSSFSheet sheet = workbook.getSheetAt(0);//获取单元格中的信息 0 是第一个sheet
        Validata(file, sheet);
        List<SpLittleCategory> littleCategoryList = new ArrayList<>();//3.读取数据，放到list集合中-循环读取
        List<String> nameCountList = new ArrayList<>();
        List<String> codeCountList = new ArrayList<>();

        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            SpLittleCategory littleCategory = new SpLittleCategory();
            //3.1.获取单元格中的信息，如果为空，直接返回
            String stringCellValue = sheet.getRow(i).getCell(0).getStringCellValue();
            if (stringCellValue == null || stringCellValue.equals("")) {
                return ResultBuildVo.success(spLittleCategoryMapper.saveBatch(littleCategoryList));
            }
            //4.一系列的验证 1.不为空字段是否为空 2.编码是否重复 3.名称是否重复 4.长度是否超出 5.状态名称是否正确
            ResultVo name = nameResultVo(sheet, nameCountList, i, littleCategory, errorMessageList);
            if (name != null) return name;
            ResultVo code = codeResultVo(sheet, codeCountList, i, littleCategory, errorMessageList);
            if (code != null) return code;
            ResultVo status = statusResultVo(sheet, i, littleCategory, errorMessageList);
            if (status != null) return status;
            ResultVo remark = remarkResultVo(sheet, i, littleCategory, errorMessageList);
            if (remark != null) return remark;
            ResultVo nameAndCode = getResultVo(nameCountList, codeCountList, errorMessageList);
            if (nameAndCode != null) return nameAndCode;
            if (errorMessageList.length() > 0) {
                return ResultBuildVo.error("导入失败，错误信息为：" + errorMessageList.substring(1, errorMessageList.length() - 1));
            }
            littleCategoryList.add(littleCategory);
        }
        //6.数据入库
        return ResultBuildVo.success(spLittleCategoryMapper.saveBatch(littleCategoryList));
    }

    @Nullable
    private ResultVo getResultVo(List<String> nameCountList, List<String> codeCountList, StringBuilder errorMessageList) {
        //验证在用户传来的文件里是否有重复数据 //1.验证编码是否重复
        List<String> codeCollect = codeCountList.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                .entrySet().stream().filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (codeCollect.size() > 0) {
            errorMessageList.append("编码重复的有：").append(codeCollect);
        } else {
            //校验编码是否在数据库中存在
            List<String> codeList = spLittleCategoryMapper.selectCodeList(codeCountList);
            if (codeList.size() > 0) {
                errorMessageList.append("在数据库中编码重复的有：").append(codeList);
            }
        }
        //2.验证名称是否重复
        List<String> nameCollect = nameCountList.stream()
                .collect(Collectors.toMap(e -> e, e -> 1, Integer::sum))
                .entrySet().stream().filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        if (nameCollect.size() > 0) {
            errorMessageList.append("名称重复的有：").append(nameCollect);
        } else {
            //校验编码是否在数据库中存在
            List<String> nameList = spLittleCategoryMapper.selectNameList(nameCountList);
            if (nameList.size() > 0) {
                errorMessageList.append("在数据库中名称重复的有：").append(nameList);
            }
        }
        return null;
    }

    //验证文件书否为空，文件尾缀名是否正确
    public ResultVo Validata(MultipartFile file, XSSFSheet sheet) {
        if (file == null) { //判断文件是否为空
            return ResultBuildVo.error("文件不能为空！！");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).contains("xlsx")) { //获取原本文件名并且判断是否以xlsx结尾
            return ResultBuildVo.error("文件尾缀名必须为xlsx！！");
        }

        XSSFCell cell = sheet.getRow(0).getCell(0);//获取第一行第一列的信息
        if (!cell.getStringCellValue().equals("备件小类模板")) {
            return ResultBuildVo.error("导入模板错误！！");
        }
        return ResultBuildVo.success(file);
    }

    @Override
    public ResultVo exportFile(SubclassExportVo subclassExportVo, HttpServletResponse response) throws Exception {
        //1.获取数据
        List<SpLittleCategory> spLittleCategoryList = spLittleCategoryMapper.exportFile(subclassExportVo);

        ServletOutputStream out = response.getOutputStream();
        // response.setContentType("application/json;charset=utf-8");
        POIClass.toPackageOs(response, "备件小类导出模板.xlsx");
        InputStream in = ExportUtil.toPackageIn(("static/备件小类导出模板.xlsx"));
        //2.读取模板中的数据
        //使用poi读取模板中的数据，并且把查询到的信息写入到模板中
        //in模板信息   out，响应给浏览器的内容
        writeDataToExcel(in, "Sheet1", spLittleCategoryList, out);
        return ResultBuildVo.success();
    }

    public void writeDataToExcel(InputStream in, String sheetName,
                                 List<SpLittleCategory> resultList, ServletOutputStream out) throws Exception {
        //POi读取模板
        XSSFWorkbook wb = new XSSFWorkbook(in);
        //读取sheet1中的数据
        Sheet sheet = wb.getSheet(sheetName);
        if (sheet != null) {
            //向sheet1中赋值，设置样式
            toResultListValueInfo(sheet, resultList);
        }
        //把数据写入到输出流中
        wb.write(out);
        //关闭poi方法
        wb.close();
    }

    private void toResultListValueInfo(Sheet sheet, List<SpLittleCategory> plantList) {
        //从第4行开始赋值
        int row_column = 4;
        int xuhao = 1;
        //遍历数据集合
        for (SpLittleCategory obj : plantList) {
            //创建一行的方法
            Row row = sheet.createRow(row_column);
            // 给第1列序号赋值赋值
            POIClass.toCellValue(row, 0, xuhao + "");
            // 给第2列名称赋值
            POIClass.toCellValue(row, 1, obj.getName() + "");
            // 给第3列编码赋值
            POIClass.toCellValue(row, 2, obj.getCode() + "");
            //给状态赋值
            //如果状态为1，赋值为启用，如果状态为0，赋值为禁用
            String status = obj.getStatus() ? "启用" : "禁用";
            obj.setStatusName(status);
            POIClass.toCellValue(row, 3, obj.getStatusName() + "");
            //给描述赋值
            POIClass.toCellValue(row, 4, obj.getRemark() + "");
            row_column++;
            xuhao++;
        }
    }
}