package com.wyx.fileanalysis.util;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/27
 * @Date : 2020/5/27
 */
public interface IExcelRowReader {
	/**
	 * 业务逻辑实现方法
	 *
	 * @param sheetIndex
	 * @param curRow
	 * @param rowlist
	 */
	void getRows(int sheetIndex, int curRow, List<String> rowlist);
}
