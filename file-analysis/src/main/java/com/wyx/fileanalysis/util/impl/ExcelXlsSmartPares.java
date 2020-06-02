package com.wyx.fileanalysis.util.impl;

import com.alibaba.fastjson.JSONObject;
import com.wyx.fileanalysis.constant.Constant;
import com.wyx.fileanalysis.model.FormFileDTO;
import com.wyx.fileanalysis.model.FromPageDataDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.util.CollectionUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author : Just wyx
 * @Description : excel_xls智能解析
 * @Date : 2020/6/1
 */
public class ExcelXlsSmartPares implements HSSFListener {



	private POIFSFileSystem fs;

	/**
	 * 解析返回对象
	 */
	private FormFileDTO formFileDTO;

	/**
	 * 当前行，Map<列标题, 列内容>,例：map<A, 品牌><B, 型号>
	 */
	private Map<Integer, String> curRowColCodeValueMap;
	private List<Map<Integer, String>> curRowColCodeValueMapList;

	/**
	 * sheet,解析启始位，从0开始，包含当前值
	 */
	private int sheetBeginIndex;
	/**
	 * sheet,解析结束位，从0开始，不包含当前值
	 */
	private int sheetEndIndex;
	/**
	 * 行,解析启始位，从0开始，包含当前值
	 */
	private int rowBeginIndex;
	/**
	 * 行,解析结束位，从0开始，不包含当前值
	 */
	private int rowEndIndex;
	/**
	 * 列,解析启始位，从0开始，包含当前值
	 */
	private int colBeginIndex;
	/**
	 * 列,解析结束位，从0开始，不包含当前值
	 */
	private int colEndIndex;
	/**
	 * 当前解析的sheet页索引，有效的索引从0开始
	 */
	private int curSheetIndex = -1;
	/**
	 * 当前行
	 */
	private int curRowIndex;
	/**
	 * 当前列
	 */
	private int curColIndex;
	/**
	 * 当前值
	 */
	private String curValue;

	/**
	 * 最大的列值
	 */
	private int maxColNum;











//	private int minColumns = -1;


//
//	private int lastRowNumber;
//
//	private int lastColumnNumber;

	/** Should we output the formula, or the value it has? */
	private boolean outputFormulaValues = true;

	/** For parsing Formulas */
	private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;

	// excel2003工作薄
	private HSSFWorkbook stubWorkbook;

	// Records we pick up as we process
	private SSTRecord sstRecord;

	private FormatTrackingHSSFListener formatListener;

	// 表索引
//	private int sheetIndex = -1;

	private BoundSheetRecord[] orderedBSRs;

	@SuppressWarnings("unchecked")
	private ArrayList boundSheetRecords = new ArrayList();

	// For handling formulas with string results
	private int nextRow;

	private int nextColumn;

	private boolean outputNextStringRecord;

//	// 当前行
//	private int curRow = 0;

	// 存储行记录的容器
//	private List<String> rowlist = new ArrayList<String>();

	@SuppressWarnings("unused")
	private String sheetName;

//	private IExcelRowReader rowReader;

//	public void setRowReader(IExcelRowReader rowReader) {
//		this.rowReader = rowReader;
//	}

	public ExcelXlsSmartPares(String path, String failName) throws Exception {
		if (StringUtils.isEmpty(path)) {
			throw new Exception("文件不能空");
		}
		this.sheetBeginIndex = Constant.EXCEL_PARES_SHEET_BEGIN_INDEX_DEFAULT;
		this.sheetEndIndex = Constant.EXCEL_PARES_SHEET_END_INDEX_DEFAULT;
		this.rowBeginIndex = Constant.EXCEL_PARES_ROW_BEGIN_INDEX_DEFAULT;
		this.rowEndIndex = Constant.EXCEL_PARES_ROW_END_INDEX_DEFAULT;
		this.colBeginIndex = Constant.EXCEL_PARES_COL_BEGIN_INDEX_DEFAULT;
		this.colEndIndex = Constant.EXCEL_PARES_COL_END_INDEX_DEFAULT;

		// 初始化返回的数据
		formFileDTO = new FormFileDTO(failName);
		this.process(path);
	}

	/**
	 * 遍历excel下所有的sheet
	 *
	 * @throws IOException
	 */
	public void process(String path) {
		try {
			this.fs = new POIFSFileSystem(new FileInputStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		MissingRecordAwareHSSFListener listener = new MissingRecordAwareHSSFListener(this);
		formatListener = new FormatTrackingHSSFListener(listener);
		HSSFEventFactory factory = new HSSFEventFactory();
		HSSFRequest request = new HSSFRequest();
		if (outputFormulaValues) {
			request.addListenerForAllRecords(formatListener);
		} else {
			workbookBuildingListener = new EventWorkbookBuilder.SheetRecordCollectingListener(formatListener);
			request.addListenerForAllRecords(workbookBuildingListener);
		}
		try {
			factory.processWorkbookEvents(request, fs);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * HSSFListener 监听方法，处理 Record
	 */
	@SuppressWarnings("unchecked")
	public void processRecord(Record record) {
		curRowIndex = -1;
		curColIndex = -1;
		curValue = "";
		switch (record.getSid()) {
			case BoundSheetRecord.sid:
				boundSheetRecords.add(record);
				break;
			case BOFRecord.sid:
				BOFRecord br = (BOFRecord) record;
				if (br.getType() == BOFRecord.TYPE_WORKSHEET) {
//					// 如果有需要，则建立子工作薄
//					if (workbookBuildingListener != null && stubWorkbook == null) {
//						stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
//					}

					// init
					this.curRowIndex = -1;
					maxColNum = 0;
					curRowColCodeValueMap = new HashMap<>();
					curRowColCodeValueMapList = new ArrayList<>();

					curSheetIndex++;
					if (orderedBSRs == null) {
						orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
					}
					sheetName = orderedBSRs[curSheetIndex].getSheetname();
				}
				break;
			case SSTRecord.sid:
				sstRecord = (SSTRecord) record;
				break;

			case BlankRecord.sid:
				// Represents a column in a row with no value but with styling.
				BlankRecord brec = (BlankRecord) record;
				curRowIndex = brec.getRow();
				curColIndex = brec.getColumn();
				break;
			case BoolErrRecord.sid:
				// 单元格为布尔类型
				BoolErrRecord berec = (BoolErrRecord) record;
				curRowIndex = berec.getRow();
				curColIndex = berec.getColumn();
				curValue = berec.getBooleanValue() + "";
				break;
			case FormulaRecord.sid:
				// 单元格为公式类型
				FormulaRecord frec = (FormulaRecord) record;
				curRowIndex = frec.getRow();
				curColIndex = frec.getColumn();
				if (outputFormulaValues) {
					if (Double.isNaN(frec.getValue())) {
						// Formula result is a string
						// This is stored in the next record
						outputNextStringRecord = true;
						nextRow = frec.getRow();
						nextColumn = frec.getColumn();
					} else {
						curValue = formatListener.formatNumberDateCell(frec);
					}
				} else {
					curValue = '"' + HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
				}
				break;
			case StringRecord.sid:
				// 单元格中公式的字符串
				if (outputNextStringRecord) {
					// String for formula
					StringRecord srec = (StringRecord) record;
					curValue = srec.getString();
					curRowIndex = nextRow;
					curColIndex = nextColumn;
					outputNextStringRecord = false;
				}
				break;
			case LabelRecord.sid:
				LabelRecord lrec = (LabelRecord) record;
				curRowIndex = lrec.getRow();
				curColIndex = lrec.getColumn();
				curValue = lrec.getValue();
				break;
			case LabelSSTRecord.sid: // 单元格为字符串类型
				LabelSSTRecord lsrec = (LabelSSTRecord) record;
				curRowIndex = lsrec.getRow();
				curColIndex = lsrec.getColumn();
				if (sstRecord != null) {
					curValue = sstRecord.getString(lsrec.getSSTIndex()).toString().trim();
				}
				break;
			case NumberRecord.sid: // 单元格为数字类型
				NumberRecord numrec = (NumberRecord) record;
				curRowIndex = numrec.getRow();
				curColIndex = numrec.getColumn();
				curValue = formatListener.formatNumberDateCell(numrec).trim();
				break;
			case EOFRecord.sid:
				// Marks the end of records belonging to a particular object in the HSSF File
				if (CollectionUtils.isEmpty(curRowColCodeValueMapList)) {
					break;
				}
				int rowSize = curRowColCodeValueMapList.size();
				int colSize = maxColNum + 1;
				String[][] data = new String[rowSize][colSize];
				String[] colData;
				for (int i = 0; i < curRowColCodeValueMapList.size(); i++) {
					curRowColCodeValueMap = curRowColCodeValueMapList.get(i);
					colData = new String[colSize];
					Arrays.fill(colData, "");
					for (Map.Entry<Integer, String> entry : curRowColCodeValueMap.entrySet()) {
						colData[entry.getKey()] = entry.getValue();
					}
					data[i] = colData;
				}
				formFileDTO.getPageDataList().add(new FromPageDataDTO()
						.setName("sheet" + curSheetIndex)
						.setRowNum(rowSize)
						.setColNum(colSize)
						.setData(data));

				break;
			default:
				break;
		}
		// 空值的操作
		if (record instanceof MissingCellDummyRecord) {
			MissingCellDummyRecord mc = (MissingCellDummyRecord) record;
			curRowIndex = mc.getRow();
			curColIndex = mc.getColumn();
		}
		// 行结束时的操作
		if (record instanceof LastCellOfRowDummyRecord) {
			++this.curRowIndex;

			if (!CollectionUtils.isEmpty(curRowColCodeValueMap)) {
				curRowColCodeValueMapList.add(curRowColCodeValueMap);
				curRowColCodeValueMap = new HashMap<>();
			}
		}

		boolean isRowAccess = curRowIndex >= rowBeginIndex && curRowIndex < rowEndIndex;
		boolean isColAccess = curColIndex >= colBeginIndex && curColIndex < colEndIndex;
		if (isRowAccess && isColAccess) {
			maxColNum = maxColNum > curColIndex ? maxColNum : curColIndex;
			curRowColCodeValueMap.put(curColIndex, curValue);
		}
	}

	public static void main(String[] args) throws Exception {
		Long time = System.currentTimeMillis();

		ExcelXlsSmartPares reader = new ExcelXlsSmartPares("file/test_hssf.xls", "文件名.xlsx");
		System.out.println(JSONObject.toJSONString(reader.formFileDTO));

		Long endtime = System.currentTimeMillis();
		System.out.println("解析数据耗时" + (endtime - time) + "毫秒");
	}



}
