package com.yp.spare.spareCostPrice.serviceImpl;

import com.yp.spare.spareCostPrice.bean.QueryPageVo;
import com.yp.spare.spareCostPrice.bean.SpareCostPrice;
import com.yp.spare.spareCostPrice.bean.importSpare;
import com.yp.spare.spareCostPrice.mapper.SpareCostPriceMapper;
import com.yp.spare.spareCostPrice.service.SpareCostPriceService;
import com.yp.spare.utils.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 创建人:唐山吴彦祖
 * 创建时间:2023/2/22 14:30
 * 版本号:1.0
 */
@Service
public class SpareCostPriceServiceImpl implements SpareCostPriceService {
    Date date = new Date();
    @Autowired
    private SpareCostPriceMapper spareCostPriceMapper;

    @Override
    public ResultVo queryPage(QueryPageVo queryPageVo) {
        //计算分页数
        Integer offSize = (queryPageVo.getPageIndex() - 1) * ParamUtil.PAGE_COUNT;
        queryPageVo.setOffSize(offSize);
        queryPageVo.setPageSize(ParamUtil.PAGE_COUNT);
        //查询数据
        List<SpareCostPrice> spareCostPrices = spareCostPriceMapper.queryPage(queryPageVo);
        for (SpareCostPrice spareCostPrice : spareCostPrices) {
            if (spareCostPrice.getStatus()) {
                spareCostPrice.setStatusName("启用");
            } else {
                spareCostPrice.setStatusName("禁用");
            }
        }
        //查询总数
        Integer totalCount = spareCostPriceMapper.getPriceCount(queryPageVo);
        return PageInfoUtil.getResultVoByPrice(queryPageVo, spareCostPrices, totalCount);
    }

    @Override
    @Transactional
    public ResultVo addSpareCostPrice(List<SpareCostPrice> spareCostPriceList) {
        //循环遍历，判断是否有开始时间相同的数据
        for (SpareCostPrice spareCostPrice : spareCostPriceList) {
            //分别根据备件，法人，工厂，查询出所有的数据
            List<SpareCostPrice> spareCostPrices = spareCostPriceMapper.getSpareCorFac(spareCostPrice.getSpareId(), spareCostPrice.getCorId(), spareCostPrice.getFacCode());
            //循环遍历，判断是否有开始时间相同的数据
            for (SpareCostPrice oldData : spareCostPrices) {
                if (spareCostPrices.size() == 0) {
                    //如果数据不存在，则新增一条数据
                    spareCostPrice.setAddTime(date).setAddUser("admin").setUpdateTime(date).setUpdateUser("admin");
                } else if (spareCostPrice.getEffectiveTime().isEqual(oldData.getEffectiveTime()) && spareCostPrice.getInvalidTime().isAfter(oldData.getInvalidTime())) {
                    //如果数据存在，判断是否有开始时间相同的一条数据,如果开始时间相同，并且结束时间在原有数据的结束时间之后，则以最新提交的覆盖原有的，
                    oldData.setInvalidTime(spareCostPrice.getInvalidTime()).setUpdateTime(date).setUpdateUser("admin");
                    spareCostPriceMapper.updateSpareCostPrice(oldData);
                } else if (spareCostPrice.getEffectiveTime().isEqual(oldData.getEffectiveTime())
                        && spareCostPrice.getInvalidTime().isBefore(oldData.getInvalidTime())) {
                    //如果开始时间相同，结束时间在原有数据的结束时间之前，当前数据正常插入，把历史数据的开始日期变更为当前结束日期的后一天。
                    oldData.setEffectiveTime(spareCostPrice.getInvalidTime().plusDays(1));
                    spareCostPriceMapper.updateSpareCostPrice(oldData);
                } else if (spareCostPrice.getEffectiveTime().isBefore(oldData.getEffectiveTime())
                        && spareCostPrice.getInvalidTime().isBefore(oldData.getInvalidTime())) {
                    //如果开始时间在原有数据的开始时间之前,结束时间在原有数据的结束时间之前，直接插入当前数据，变更历史数据的开始日期为当前结束日期的后一天。
                    spareCostPrice.setInvalidTime(spareCostPrice.getInvalidTime()).setAddTime(date).setAddUser("admin").setUpdateTime(date).setUpdateUser("admin");
                    oldData.setEffectiveTime(spareCostPrice.getInvalidTime().plusDays(1));
                    spareCostPriceMapper.updateSpareCostPrice(oldData);
                } else if (spareCostPrice.getEffectiveTime().isBefore(oldData.getEffectiveTime())
                        && spareCostPrice.getInvalidTime().isAfter(oldData.getInvalidTime())) {
                    //如果开始时间在原有数据的开始时间之前,结束时间在原有数据的结束时间之后，直接覆盖
                    oldData.setEffectiveTime(spareCostPrice.getEffectiveTime()).setInvalidTime(spareCostPrice.getInvalidTime()).setUpdateTime(date).setUpdateUser("admin");
                    spareCostPriceMapper.updateSpareCostPrice(oldData);
                } else if (spareCostPrice.getEffectiveTime().isAfter(oldData.getEffectiveTime()) && spareCostPrice.getInvalidTime().isBefore(oldData.getInvalidTime())) {
                    //如果开始时间在原有数据之后，结束时间在原有数据之前，新数据直接添加，历史数据分为两条：
                    // 第一条，把历史数据结束时间变更为当前开始日期数据的前一天。
                    oldData.setInvalidTime(spareCostPrice.getEffectiveTime().minusDays(1)).setUpdateTime(date).setUpdateUser("admin");
                    spareCostPriceMapper.updateSpareCostPrice(oldData);
                    //第二条，新增一条和历史数据一样的但把历史开始日期变更为当前结束日期的后一天。
                    SpareCostPrice middleData = new SpareCostPrice();
                    middleData.setAddTime(date).setAddUser("admin").setUpdateTime(date).setUpdateUser("admin").setCorId(oldData.getCorId())
                            .setCurrencyId(oldData.getCurrencyId()).setEffectiveTime(spareCostPrice.getInvalidTime().plusDays(1)).setFacCode(oldData.getFacCode())
                            .setInvalidTime(oldData.getInvalidTime()).setPriceType(oldData.getPriceType()).setSpareCode(oldData.getSpareCode()).setSpareCostPrice(oldData.getSpareCostPrice())
                            .setSpareCostPriceRemark(oldData.getSpareCostPriceRemark()).setSpareId(oldData.getSpareId()).setSpareName(oldData.getSpareName())
                            .setStatus(oldData.getStatus()).setStatusName(oldData.getStatusName()).setTaxrateId(oldData.getTaxrateId()).setUnitType(oldData.getUnitType());
                    spareCostPriceMapper.addSpareCostPrice(middleData);
                } else if (spareCostPrice.getEffectiveTime().isAfter(oldData.getEffectiveTime()) && spareCostPrice.getInvalidTime().isAfter(oldData.getInvalidTime())) {
//                如果开始时间在原有数据之后，结束时间在原有数据之后，直接插入当前数据，变更历史数据的结束日期为当前开始日期的前一天。
                    oldData.setInvalidTime(spareCostPrice.getEffectiveTime().minusDays(1));
                    spareCostPriceMapper.updateSpareCostPrice(oldData);
                }
                spareCostPriceMapper.addSpareCostPrice(spareCostPrice);
            }
        }

        return ResultBuildVo.success();
    }

    @Override
    public Object updateSpareCostPrice(SpareCostPrice spareCostPrice) {
        List<SpareCostPrice> spareCostPrices = spareCostPriceMapper.getSpareCorFac(spareCostPrice.getSpareId(), spareCostPrice.getCorId(), spareCostPrice.getFacCode());
        //修改只能修改结束时间，开始时间不能修改，同一个时间段内，同法人、同工厂的同一备件不能有两个价格，则以最新提交的覆盖原有的 时间范围重叠，则系统将较早的数据结束时间自动变更为较晚数据的开始时间前一天；
        for (SpareCostPrice oldData : spareCostPrices) {
            if (spareCostPrice.getEffectiveTime().isEqual(oldData.getEffectiveTime()) && spareCostPrice.getInvalidTime().isAfter(oldData.getInvalidTime())) {
                //如果数据存在，判断是否有开始时间相同的一条数据,如果开始时间相同，并且结束时间在原有数据的结束时间之后，则以最新提交的覆盖原有的，
                oldData.setInvalidTime(spareCostPrice.getInvalidTime()).setUpdateTime(date).setUpdateUser("admin");
                spareCostPriceMapper.updateSpareCostPrice(oldData);
            } else if (spareCostPrice.getEffectiveTime().isEqual(oldData.getEffectiveTime())
                    && spareCostPrice.getInvalidTime().isBefore(oldData.getInvalidTime())) {
                //如果开始时间相同，结束时间在原有数据的结束时间之前，当前数据正常插入，把历史数据的开始日期变更为当前结束日期的后一天。
                oldData.setEffectiveTime(spareCostPrice.getInvalidTime().plusDays(1));
                spareCostPriceMapper.updateSpareCostPrice(oldData);
                spareCostPriceMapper.addSpareCostPrice(spareCostPrice);
            } else if (spareCostPrice.getEffectiveTime().isBefore(oldData.getEffectiveTime())
                    && spareCostPrice.getInvalidTime().isBefore(oldData.getInvalidTime())) {
                //如果开始时间在原有数据的开始时间之前,结束时间在原有数据的结束时间之前，直接插入当前数据，变更历史数据的开始日期为当前结束日期的后一天。
                spareCostPrice.setInvalidTime(spareCostPrice.getInvalidTime()).setAddTime(date).setAddUser("admin").setUpdateTime(date).setUpdateUser("admin");
                spareCostPriceMapper.addSpareCostPrice(spareCostPrice);
                oldData.setEffectiveTime(spareCostPrice.getInvalidTime().plusDays(1));
                spareCostPriceMapper.updateSpareCostPrice(oldData);
            }
        }
        return ResultBuildVo.success();
    }

    @Override
    public Object updateStatus(SpareCostPrice spareCostPrice) {
        //改一个时间段的状态，其他时间段的状态不变
        Integer flag = spareCostPriceMapper.updateStatus(spareCostPrice.getSpareCostPriceId());
        if (flag < 1) {
            return ResultBuildVo.error("修改失败");
        }
        return ResultBuildVo.success();
    }

    @Override
    public ResultVo poiFile(MultipartFile file, importSpare spareCondition) throws Exception {
        List<SpareCostPrice> selectSparePrice = spareCostPriceMapper.selectSparePrice(spareCondition);
        StringBuilder errorMessageList = new StringBuilder();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());//2.获取工作簿的开头 查看是否满足要求
        XSSFSheet sheet = workbook.getSheetAt(0);//获取单元格中的信息 0 是第一个sheet
        Validata(file, sheet);
        List<SpareCostPrice> spareCostList = new ArrayList<>();//3.读取数据，放到list集合中-循环读取
        List<String> nameCountList = new ArrayList<>();
        List<String> codeCountList = new ArrayList<>();
        List<String> taxTypeCountList = new ArrayList<>();
        List<String> spareCostPriceList = new ArrayList<>();
        List<String> effectiveTimeList = new ArrayList<>();
        List<String> invalidTimeList = new ArrayList<>();
        for (int i = 4; i <= sheet.getLastRowNum(); i++) {
            SpareCostPrice spareCostPrice = new SpareCostPrice();
            //3.1.获取单元格中的信息，如果为空，直接返回
            String stringCellValue = sheet.getRow(i).getCell(0).getStringCellValue();
            if (stringCellValue == null || stringCellValue.equals("")) {
                return ResultBuildVo.success(spareCostPriceMapper.addSpareCostPriceList(spareCostList));
            }
            //4.一系列的验证 1.不为空字段是否为空 2.编码是否重复 3.名称是否重复 4.长度是否超出 5.状态名称是否正确
            ResultVo name = nameResultVo(sheet, nameCountList, i, spareCostPrice, errorMessageList);
            if (name != null) return name;
            ResultVo code = codeResultVo(sheet, codeCountList, i, spareCostPrice, errorMessageList);
            if (code != null) return code;
            ResultVo taxType = taxTypeResultVo(sheet, taxTypeCountList, i, spareCostPrice, errorMessageList);
            if (taxType != null) return taxType;
            ResultVo price = spareCostPriceListResultVo(sheet, spareCostPriceList, i, spareCostPrice, errorMessageList);
            if (price != null) return price;
            ResultVo effectiveTime = effectiveTimeResultVo(sheet, effectiveTimeList, i, spareCostPrice, errorMessageList);
            if (effectiveTime != null) return effectiveTime;
            ResultVo invalidTime = invalidTimeResultVo(sheet, invalidTimeList, i, spareCostPrice, errorMessageList);
            if (invalidTime != null) return invalidTime;
            if (errorMessageList.length() > 0) {
                return ResultBuildVo.error("导入失败，错误信息为：" + errorMessageList.substring(1, errorMessageList.length() - 1));
            }
            spareCostList.add(spareCostPrice);
        }

        return ResultBuildVo.success(spareCostPriceMapper.addSpareCostPriceList(spareCostList));
    }


    private ResultVo Validata(MultipartFile file, XSSFSheet sheet) {
        if (file == null) { //判断文件是否为空
            return ResultBuildVo.error("文件不能为空！！");
        }
        if (!Objects.requireNonNull(file.getOriginalFilename()).contains("xlsx")) { //获取原本文件名并且判断是否以xlsx结尾
            return ResultBuildVo.error("文件尾缀名必须为xlsx！！");
        }

        XSSFCell cell = sheet.getRow(0).getCell(0);//获取第一行第一列的信息
        if (!cell.getStringCellValue().equals("备件成本价模板")) {
            return ResultBuildVo.error("导入模板错误！！");
        }
        return ResultBuildVo.success(file);
    }

    @Nullable
    private ResultVo nameResultVo(XSSFSheet sheet, List<String> nameCountList, int i, SpareCostPrice spareCostPrice, StringBuilder errorMessageList) {
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
                spareCostPrice.setSpareName(name);
                nameCountList.add(name);
            }
        }
        return null;
    }

    @Nullable
    private ResultVo codeResultVo(XSSFSheet sheet, List<String> codeCountList, int i, SpareCostPrice spareCostPrice, StringBuilder errorMessageList) {
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
                spareCostPrice.setSpareCode(code);
                codeCountList.add(code);
            }
        }
        return null;
    }

    @Nullable
    private ResultVo taxTypeResultVo(XSSFSheet sheet, List<String> taxTypeCountList, int i, SpareCostPrice spareCostPrice, StringBuilder errorMessageList) {
        XSSFCell taxTypeCell = sheet.getRow(i).getCell(3);
        if (taxTypeCell == null) {
            errorMessageList.append("第").append(i + 1).append("行的税率类型不能为空！！");
        } else {
            String taxType = taxTypeCell.getStringCellValue();
            if (StringUtils.isEmpty(taxType)) {
                errorMessageList.append("第").append(i + 1).append("行的税率类型不能为空！！");
            } else if (taxType.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的税率类型长度不能超过200！！");
            } else {
                spareCostPrice.setTaxType(taxType);
                taxTypeCountList.add(taxType);
            }
        }
        return null;
    }

    @Nullable
    private ResultVo spareCostPriceListResultVo(XSSFSheet sheet, List<String> priceList, int i, SpareCostPrice spareCostPrice, StringBuilder errorMessageList) {
        XSSFCell spareCostPriceCell = sheet.getRow(i).getCell(4);
        if (spareCostPriceCell == null) {//避免了空字符串传进来为空指针的异常
            errorMessageList.append("第").append(i + 1).append("行的价格不能为空！！");
        } else {
            String spareCostPrice1 = spareCostPriceCell.getStringCellValue();
            if (StringUtils.isEmpty(spareCostPrice1)) {
                errorMessageList.append("第").append(i + 1).append("行的价格不能为空！！");
            } else if (spareCostPrice1.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的价格长度不能超过200！！");
            } else {
                spareCostPrice.setSpareCostPrice(spareCostPrice1);
                priceList.add(spareCostPrice1);
            }
        }
        return null;
    }

    @Nullable
    private ResultVo effectiveTimeResultVo(XSSFSheet sheet, List<String> effectiveTimeList, int i, SpareCostPrice spareCostPrice, StringBuilder errorMessageList) {
        XSSFCell effectiveTimeCell = sheet.getRow(i).getCell(5);
        if (effectiveTimeCell == null) {//避免了空字符串传进来为空指针的异常
            errorMessageList.append("第").append(i + 1).append("行的开始时间不能为空！！");
        } else {
            String effectiveTime = effectiveTimeCell.getStringCellValue();
            if (StringUtils.isEmpty(effectiveTime)) {
                errorMessageList.append("第").append(i + 1).append("行的开始时间不能为空！！");
            } else if (effectiveTime.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的开始时间长度不能超过200！！");
            } else {
                spareCostPrice.setEffectiveTime(LocalDate.parse(effectiveTime));
                effectiveTimeList.add(effectiveTime);
            }
        }
        return null;
    }

    @Nullable
    private ResultVo invalidTimeResultVo(XSSFSheet sheet, List<String> effectiveTimeList, int i, SpareCostPrice spareCostPrice, StringBuilder errorMessageList) {
        XSSFCell invalidTimeCell = sheet.getRow(i).getCell(6);
        if (invalidTimeCell == null) {//避免了空字符串传进来为空指针的异常
            errorMessageList.append("第").append(i + 1).append("行的结束时间不能为空！！");
        } else {
            String invalidTime = invalidTimeCell.getStringCellValue();
            if (StringUtils.isEmpty(invalidTime)) {
                errorMessageList.append("第").append(i + 1).append("行的结束时间不能为空！！");
            } else if (invalidTime.length() > 50) {
                errorMessageList.append("第").append(i + 1).append("行的结束时间长度不能超过200！！");
            } else {
                spareCostPrice.setInvalidTime(LocalDate.parse(invalidTime));
                effectiveTimeList.add(invalidTime);
            }
        }
        return null;
    }

    @Override
    public ResultVo exportFile(SpareCostPrice subclassExportVo, HttpServletResponse response) throws Exception {
        //1.获取数据
        List<SpareCostPrice> spLittleCategoryList = spareCostPriceMapper.exportFile(subclassExportVo);
        ServletOutputStream out = response.getOutputStream();
        // response.setContentType("application/json;charset=utf-8");
        POIClass.toPackageOs(response, "备件成本价导出模板.xlsx");
        InputStream in = ExportUtil.toPackageIn(("templates/备件成本价导出模板.xlsx"));
        //2.读取模板中的数据
        //使用poi读取模板中的数据，并且把查询到的信息写入到模板中
        //in模板信息   out，响应给浏览器的内容
        writeDataToExcel(in, "Sheet1", spLittleCategoryList, out);
        return ResultBuildVo.success();
    }

    public void writeDataToExcel(InputStream in, String sheetName,
                                 List<SpareCostPrice> resultList, ServletOutputStream out) throws Exception {
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

    private void toResultListValueInfo(Sheet sheet, List<SpareCostPrice> plantList) {
        //从第4行开始赋值
        int row_column = 4;
        int xuhao = 1;
        //遍历数据集合
        for (SpareCostPrice obj : plantList) {
            //创建一行的方法
            Row row = sheet.createRow(row_column);
            // 给第1列序号赋值赋值
            POIClass.toCellValue(row, 0, xuhao + "");
            // 给第2列名称赋值
            POIClass.toCellValue(row, 1, obj.getSpareName() + "");
            // 给第3列编码赋值
            POIClass.toCellValue(row, 2, obj.getSpareCode() + "");

            POIClass.toCellValue(row, 3, obj.getStatusName() + "");
            //给描述赋值
            row_column++;
            xuhao++;
        }
    }
}



