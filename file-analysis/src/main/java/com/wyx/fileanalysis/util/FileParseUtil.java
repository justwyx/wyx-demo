package com.wyx.fileanalysis.util;

import com.wyx.fileanalysis.util.impl.ExcelXlsReader;
import com.wyx.fileanalysis.util.impl.ExcelXlsxReader;

/**
 * @Description : 文件解析-util
 * @author : Just wyx
 * @Date : 2020/5/27
 */
public class FileParseUtil {
	// excel2003扩展名
	public static final String EXCEL03_EXTENSION = ".xls";
	// excel2007扩展名
	public static final String EXCEL07_EXTENSION = ".xlsx";

	/**
	 * 读取Excel文件，可能是03也可能是07版本
	 *
	 * @param excel03
	 * @param excel07
	 * @param fileName
	 * @throws Exception
	 */
	public static void readExcel(IExcelRowReader reader, String fileName) throws Exception {
		// 处理excel2003文件
		if (fileName.endsWith(EXCEL03_EXTENSION)) {
			ExcelXlsReader exceXls = new ExcelXlsReader();
			exceXls.setRowReader(reader);
			exceXls.process(fileName);
			// 处理excel2007文件
		} else if (fileName.endsWith(EXCEL07_EXTENSION)) {
			ExcelXlsxReader exceXlsx = new ExcelXlsxReader();
			exceXlsx.setRowReader(reader);
			exceXlsx.process(fileName);
		} else {
			throw new Exception("文件格式错误，fileName的扩展名只能是xls或xlsx。");
		}
	}

	/**
	 * 测试
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		IExcelRowReader rowReader = new ExcelRowReader();
		FileParseUtil.readExcel(rowReader, "file/prod_price_default.xlsx");
	}
}
