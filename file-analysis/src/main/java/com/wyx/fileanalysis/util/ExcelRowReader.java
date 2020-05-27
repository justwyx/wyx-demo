package com.wyx.fileanalysis.util;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/27
 * @Date : 2020/5/27
 */
public class ExcelRowReader implements IExcelRowReader {

	@Override
	public void getRows(int sheetIndex, int curRow, List<String> rowlist) {
		System.out.print(curRow+" ");
		for (int i = 0; i < rowlist.size(); i++) {
			System.out.print(rowlist.get(i)==""?"*":rowlist.get(i) + " ");
		}
		System.out.println();
	}

}
