package com.wyx.fileanalysis.constant;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/27
 * @Date : 2020/5/27
 */
public class Constant {
	/**
	 * FROM_FILE 数据页数——默认值
	 */
	public static final Integer FROM_FILE_DATA_PAGE_SIZE_DEFAULT = 1;
	/**
	 * FROM_FILE 解析行数——默认值
	 */
	public static final Integer FROM_FILE_DATA_ROW_SIZE_DEFAULT = 100000;
	/**
	 * FROM_FILE 解析列数——默认值
	 */
	public static final Integer FROM_FILE_DATA_COL_SIZE_DEFAULT = 20;


	/**
	 * excel解析 sheet 起始，包含
	 */
	public static final int EXCEL_PARES_SHEET_BEGIN_INDEX_DEFAULT = 0;
	/**
	 * excel解析 sheet 结束，不包含
	 */
	public static final int EXCEL_PARES_SHEET_END_INDEX_DEFAULT = 10;
	/**
	 * excel解析 行 起始，包含
	 */
	public static final int EXCEL_PARES_ROW_BEGIN_INDEX_DEFAULT = 0;
	/**
	 * excel解析 行 结束，不包含
	 */
	public static final int EXCEL_PARES_ROW_END_INDEX_DEFAULT = 100000;
	/**
	 * excel解析 列 起始，包含
	 */
	public static final int EXCEL_PARES_COL_BEGIN_INDEX_DEFAULT = 0;
	/**
	 * excel解析 列 结束，不包含
	 */
	public static final int EXCEL_PARES_COL_END_INDEX_DEFAULT = 20;
}
