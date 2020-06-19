package com.wyx.fileanalysis.util.impl;

import com.alibaba.fastjson.JSONObject;
import com.wyx.fileanalysis.constant.Constant;
import com.wyx.fileanalysis.model.FormFileDTO;
import com.wyx.fileanalysis.model.FromPageDataDTO;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.*;


/**
 * @author : Just wyx
 * @Description : excel_xlsx智能解析
 * @Date : 2020/5/27
 */
public class ExcelXlsxSmartPares {


	/**
	 * 文件路径
	 */
	private final String path;
	/**
	 * 文件路径
	 */
	private final String failName;
	/**
	 * 要解析的列,列<B,C,D,E>
	 */
	private Set<String> paresColTitleSet;
	/**
	 * 空值的例，整列数据全为空才会在里面 例：<B,C,D,E>
	 */
	private Set<String> nullColTitleSet;
	/**
	 * 当前列是否为空,true:不是全为空
	 */
	private boolean isNotNullCurCol = false;
	/**
	 * 解析返回对象
	 */
	private FormFileDTO formFileDTO;
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
	 * 列标题——TreeSet，例：{A,B,C,D,E,F.....}
	 */
	private Set<String> colTitleTreeSet;
	/**
	 * 列标题——TreeMap，例：A:1,B:2,D:3,E:4
	 */
	private Map<String, Integer> colTitleTreeMap;
	/**
	 * 当前行，Map<列标题, 列内容>,例：map<A, 品牌><B, 型号>
	 */
	private Map<String, String> curRowColTitleValueMap;
	private List<Map<String, String>> curRowColTitleValueMapList;
	private String curCellKey;
	private String curCellValue;


	public ExcelXlsxSmartPares(String path, String fileName) throws Exception {
		if (StringUtils.isEmpty(path)) {
			throw new Exception("文件不能空");
		}
		this.sheetBeginIndex = Constant.EXCEL_PARES_SHEET_BEGIN_INDEX_DEFAULT;
		this.sheetEndIndex = Constant.EXCEL_PARES_SHEET_END_INDEX_DEFAULT;
		this.rowBeginIndex = Constant.EXCEL_PARES_ROW_BEGIN_INDEX_DEFAULT;
		this.rowEndIndex = Constant.EXCEL_PARES_ROW_END_INDEX_DEFAULT;
		this.colBeginIndex = Constant.EXCEL_PARES_COL_BEGIN_INDEX_DEFAULT;
		this.colEndIndex = Constant.EXCEL_PARES_COL_END_INDEX_DEFAULT;

		this.paresColTitleSet = ExcelUtil.getColTitleSet(this.colBeginIndex, this.colEndIndex);

		this.path = path;
		this.failName = fileName;

		formFileDTO = new FormFileDTO(this.failName);
		processSheet();
	}


	/**
	 * 指定获取第一个sheet
	 *
	 * @param 'path'
	 * @throws Exception
	 */
	private void processSheet() throws Exception {
		OPCPackage pkg = OPCPackage.open(path);
		XSSFReader r = new XSSFReader(pkg);
		SharedStringsTable sst = r.getSharedStringsTable();
		XMLReader parser = fetchSheetParser(sst);
		Iterator<InputStream> it = r.getSheetsData();
		InputStream sheet1;
		while (it.hasNext()) {
			sheet1 = it.next();
			// 指定只解析某些sheet
			++curSheetIndex;
			if (curSheetIndex < sheetBeginIndex || curSheetIndex >= sheetEndIndex) {
				continue;
			}
			// 初始化当前sheet的数据
			curRowIndex = 0;
			colTitleTreeSet = new TreeSet<>();
			curRowColTitleValueMap = new HashMap<>();
			curRowColTitleValueMapList = new ArrayList<>();

			nullColTitleSet = ExcelUtil.getColTitleSet(this.colBeginIndex, this.colEndIndex);

			InputSource sheetSource = new InputSource(sheet1);
			parser.parse(sheetSource);
			// 赋值
			formFileDTO.getPageDataList().add(buildFromPageDataDTO());
			formFileDTO.addPageNum();
		}
	}

	/**
	 * 加载sax 解析器
	 *
	 * @param sst
	 * @return
	 * @throws SAXException
	 */
	private XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser =
				XMLReaderFactory.createXMLReader(
						"org.apache.xerces.parsers.SAXParser"
				);
		ContentHandler handler = new ExcelXlsxSmartPares.PagingHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs
	 */
	private class PagingHandler extends DefaultHandler {
		private SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private String index = null;

		private PagingHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		/**
		 * 开始元素 （获取key 值）
		 */
		@Override
		public void startElement(String uri, String localName, String name,
								 Attributes attributes) throws SAXException {
			// c => 单元格
			if (name.equals("c")) {
				// 当前单元格位置
				index = attributes.getValue("r");
				// 全局的列标题名
				colTitleTreeSet.add(getColumnName());
				if (isAccess()) {
					String cellType = attributes.getValue("t");
					if (cellType != null && cellType.equals("s")) {
						nextIsString = true;
					} else {
						nextIsString = false;
					}
				}
			}
			lastContents = "";
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException {
			if (!isAccess()) {
				return;
			}
			lastContents += new String(ch, start, length);
		}

		/**
		 * 获取value
		 */
		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// 不解析跳过
			if (!isAccess()) {
				return;
			}
			// 根据SST的索引值的到单元格的真正要存储的字符串
			// 这时characters()方法可能会被调用多次
			if (nextIsString && StringUtils.isNotEmpty(lastContents) && StringUtils.isNumeric(lastContents)) {
				lastContents = new XSSFRichTextString(sst.getEntryAt(Integer.parseInt(lastContents))).toString();
			}

			if ("v".equals(name)) {
				// v => 单元格的值，如果单元格是字符串则v标签的值为该字符串在SST中的索引
				curCellKey = getColumnName();
				curCellValue = lastContents.trim();
				curRowColTitleValueMap.put(curCellKey, curCellValue);
				if (!"".equals(curCellValue)) {
					nullColTitleSet.remove(curCellKey);

					isNotNullCurCol = true;
				}
			} else {
				// 如果标签名称为 row ，这说明已到行尾，调用 optRows() 方法
				if (name.equals("row")) {
					curRowIndex++;
					if (isNotNullCurCol) {
						curRowColTitleValueMapList.add(curRowColTitleValueMap);
						isNotNullCurCol = false;
					}
					curRowColTitleValueMap = new HashMap<>();
				}
			}
			lastContents = "";
		}

		private String getColumnName() {
			if (StringUtils.isBlank(index)) {
				return "";
			}
			String[] columnNameArray = index.split("\\d");
			return columnNameArray[0];
		}

		private Integer getColumnNum() {
			String[] columnNumArray = index.split("\\D");
			for (String columnNum : columnNumArray) {
				if (StringUtils.isNotBlank(columnNum)) {
					return Integer.valueOf(columnNum);
				}
			}
			throw new AssertionError("getColumnNum error:" + index);
		}

		private boolean isAccess() {
			boolean isRow = curRowIndex >= rowBeginIndex && curRowIndex < rowEndIndex;
			boolean isCol = paresColTitleSet.contains(this.getColumnName());
			if (isRow && isCol) {
				return true;
			}
			return false;
		}
	}

	private FromPageDataDTO buildFromPageDataDTO() {
		Iterator<String> colTitleIterator = colTitleTreeSet.iterator();
		int colTitleIndex = -1;
		colTitleTreeMap = new HashMap<>();
		String colTitle;
		while (colTitleIterator.hasNext()) {
			colTitle = colTitleIterator.next();
			if (nullColTitleSet.contains(colTitle)) {
				continue;
			}
			colTitleTreeMap.put(colTitle, ++colTitleIndex);
		}

		int rowNum = curRowColTitleValueMapList.size();
		int colNum = colTitleTreeMap.size();

		String[][] data = new String[rowNum][colNum];
		String[] colData;
		for (int i = 0; i < curRowColTitleValueMapList.size(); i++) {
			curRowColTitleValueMap = curRowColTitleValueMapList.get(i);
			colData = new String[colNum];
			Arrays.fill(colData, "");
			for (Map.Entry<String, String> entry : curRowColTitleValueMap.entrySet()) {
				colData[colTitleTreeMap.get(entry.getKey())] = entry.getValue();
			}
			data[i] = colData;
		}

		return new FromPageDataDTO()
				.setName("sheet" + curSheetIndex)
				.setRowNum(rowNum)
				.setColNum(colNum)
				.setData(data);
	}


	public static void main(String[] args) throws Exception {
		Long time = System.currentTimeMillis();

		ExcelXlsxSmartPares reader = new ExcelXlsxSmartPares("file/仓库库存导入模板.xlsx", "文件名.xlsx");
		System.out.println(JSONObject.toJSONString(reader.formFileDTO));

		Long endtime = System.currentTimeMillis();
		System.out.println("解析数据耗时" + (endtime - time) + "毫秒");
	}
}
