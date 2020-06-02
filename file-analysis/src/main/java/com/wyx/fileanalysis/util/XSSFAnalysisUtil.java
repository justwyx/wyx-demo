package com.wyx.fileanalysis.util;

import com.alibaba.fastjson.JSONObject;
import com.wyx.fileanalysis.constant.Constant;
import com.wyx.fileanalysis.model.FormFileDTO;
import com.wyx.fileanalysis.model.FromPageDataDTO;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : XSSF excel2007版本解析
 * XSSFWorkbook
 * 这种形式的出现是由于HSSFWorkbook的局限性而产生的，因为其所导出的行数比较少，
 * 并且只针对Excel2003以前（包括2003）的版本的版本，
 * 所以 XSSFWookbook应运而生，其对应的是EXCEL2007以后的版本(1048576行，16384列)扩展名.xlsx，
 * 每个Sheet最多可以导出104万行，不过这样就伴随着一个OOM内存溢出的问题，
 * 原因是你所创建的sheet row cell 等此时是存在内存中的，随着数据量增大 ，内存的需求量也就增大，那么很大可能就是要OOM了。
 * @Date : 2020/5/27
 */
public class XSSFAnalysisUtil {

	/**
	 * (what) : 默认只解析第一页
	 * (why) :
	 * (how) :
	 * @param file 入参
	 * @Author : Just wyx
	 * @Date : 14:51 2020/5/27
	 * @return : com.wyx.fileanalysis.model.FormFileDTO
	 */
	public static FormFileDTO analysis(String file) throws IOException {
		return analysis(file, Constant.FROM_FILE_DATA_PAGE_SIZE_DEFAULT, Constant.FROM_FILE_DATA_ROW_SIZE_DEFAULT, Constant.FROM_FILE_DATA_COL_SIZE_DEFAULT);
	}

	/**
	 * (what) : excel2007版本解析
	 * (why) :
	 * (how) :
	 * @param file
	 * @param sheetSize 解析的最大页页
	 * @param rowSize 解析的最大行数
	 * @param colSize 解析的最大列数
	 * @Author : Just wyx
	 * @Date : 14:49 2020/5/27
	 * @return : com.wyx.fileanalysis.model.FormFileDTO
	 */
	public static FormFileDTO analysis(String file, int sheetSize, int rowSize, int colSize) throws IOException {
		InputStream ins = new FileInputStream(new File(file));
		// 得到Excel工作簿对象
		Workbook wb = WorkbookFactory.create(ins);
		ins.close();

		// 获取sheet数
		int sheetLength = wb.getNumberOfSheets();
		// sheet两小取小
		sheetSize = sheetLength < sheetSize? sheetLength : sheetSize;

		List<FromPageDataDTO> pageDataList = new ArrayList<>(sheetSize);
		for (int i = 0; i < sheetSize; i++) {
			pageDataList.add(FileAnalysisHeadlerUtil.getFromPageData(wb.getSheetAt(i), rowSize, colSize));
		}
		return new FormFileDTO().setPageNum(sheetSize).setPageDataList(pageDataList);
	}




	public static void main(String[] args) throws IOException {
		// 03版本也可以解析
//		FormFileDTO excelAsFile = analysis("file/test_hssf.xls", 10, Constant.FROM_FILE_DATA_ROW_SIZE_DEFAULT, Constant.FROM_FILE_DATA_COL_SIZE_DEFAULT);
		FormFileDTO excelAsFile = analysis("file/prod_price_default.xlsx", 10, Constant.FROM_FILE_DATA_ROW_SIZE_DEFAULT, Constant.FROM_FILE_DATA_COL_SIZE_DEFAULT);

		System.out.println(JSONObject.toJSONString(excelAsFile));
	}

}
