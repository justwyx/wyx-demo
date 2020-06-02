package com.wyx.fileanalysis.util;

import com.alibaba.fastjson.JSONObject;
import com.wyx.fileanalysis.constant.Constant;
import com.wyx.fileanalysis.model.FormFileDTO;
import com.wyx.fileanalysis.model.FromPageDataDTO;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : HSSF excel2003版本解析
 * HSSFWorkbook
 * HSSFWorkbook是操作Excel2003以前（包括2003）的版本，扩展名为.xls，所以每个Sheet局限就是导出的行数至多为65535行，一般不会发生内存不足的情况（OOM）。
 * @Date : 2020/5/27
 */
public class HSSFAnalysisUtil {

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
	 * (what) : excel2003版本解析
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
		// 得到Excel常用对象
		POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file));
		// 得到Excel工作簿对象
		Workbook wb = WorkbookFactory.create(fs);
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
		FormFileDTO excelAsFile = analysis("file/test_hssf.xls", 10, Constant.FROM_FILE_DATA_ROW_SIZE_DEFAULT, Constant.FROM_FILE_DATA_COL_SIZE_DEFAULT);
		// 2007版本的无法解析
//		FormFileDTO excelAsFile = analysis("file/test_xssf.xlsx", 10, Constant.FROM_FILE_DATA_ROW_SIZE_DEFAULT, Constant.FROM_FILE_DATA_COL_SIZE_DEFAULT);

		System.out.println(JSONObject.toJSONString(excelAsFile));
	}

}
