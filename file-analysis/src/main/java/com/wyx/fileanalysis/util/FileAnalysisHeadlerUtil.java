package com.wyx.fileanalysis.util;

import com.wyx.fileanalysis.model.FromPageDataDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/27
 * @Date : 2020/5/27
 */
public class FileAnalysisHeadlerUtil {

	/**
	 * 获取单页的数据
	 */
	public static FromPageDataDTO getFromPageData(Sheet sheet, int rowSize, int colSize) {
		// 获取sheet的总行数
		int rowLength = sheet.getPhysicalNumberOfRows();
		// 行数两小取小
		rowSize = rowLength < rowSize? rowLength : rowSize;

		List<String[]> dataList = new ArrayList<>(rowSize);
		//总列数
		int colLength;
		Row row;
		Cell cell;
		String[] cellValueArray;
		// 列的最大长度
		int tdMaxLength = 0;
		for (int i = 0; i < rowSize; i++) {
			// 得到Excel工作表的行
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			colLength = row.getPhysicalNumberOfCells();
			// 列数两小取小,不能超过最大列数
			colLength = colLength < colSize? colLength : colSize;
			// 获取最大的列数
			tdMaxLength = tdMaxLength > colLength? tdMaxLength : colLength;

			cellValueArray = new String[colLength];
			for (int j = 0; j < colLength; j++) {
				// 得到Excel工作表指定行的单元格
				cell = row.getCell(j);
				if (cell == null) {
					cellValueArray[j] = "";
					continue;
				}
				/**
				 * 为了处理：Excel异常Cannot get a text value from a numeric cell
				 * 将所有列中的内容都设置成String类型格式
				 */
				cell.setCellType(CellType.STRING);
				//获得每一列中的值
				cellValueArray[j] = cell.getStringCellValue();
			}
			dataList.add(cellValueArray);
		}
		return new FromPageDataDTO(sheet.getSheetName(), FileAnalysisHeadlerUtil.converTo(dataList, tdMaxLength));
	}
	/**
	 * 对解析内容补齐内容
	 */
	private static String[][] converTo(List<String[]> dataList, int colNum) {
		// assert dataList is not null
		// assert colNum > 0
		String[][] result = new String[dataList.size()][colNum];
		String[] data;
		for (int i = 0; i < dataList.size(); i++) {
			data = dataList.get(i);
			for (int j = 0; j < colNum; j++) {
				result[i][j] = j >= data.length? "" : data[j];
			}
		}
		return result;
	}
}
