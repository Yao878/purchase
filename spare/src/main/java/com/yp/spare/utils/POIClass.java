package com.yp.spare.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.io.ClassPathResource;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

public class POIClass {
	/**
	 * 封装返回的流信息
	 */
	public static void toPackageOs(HttpServletResponse response, String fileName) throws Exception {
		//设置编码格式
		response.setContentType("application/octet-stream;charset=utf-8");
		//拼接名称加上日期 计划大类2019-09-26
		String outFileName = fileName + new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		//给输出的文件设置名称
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(outFileName.getBytes(), StandardCharsets.ISO_8859_1) + ".xlsx");
	}

	/**
	 * 生成模板输入流
	 *
	 * @param temPath
	 * @return
	 * @throws Exception
	 */
	public static InputStream toPackageIn(String temPath) throws Exception {
		return new ClassPathResource((temPath)).getInputStream();
	}

	// 给具体的某个行中的某个列赋值
	public static void toCellValue(Row row, int cellColumn, String value) {
		Cell cell = row.createCell(cellColumn);
		cell.setCellValue(value);
	}

}
