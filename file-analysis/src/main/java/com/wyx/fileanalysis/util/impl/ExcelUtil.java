package com.wyx.fileanalysis.util.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/1
 * @Date : 2020/6/1
 */
public class ExcelUtil {
	private static final String[] colTitleArray = {"A","B","C","D","E",
			"F","G","H","I","J",
			"K","L","M","N","O",
			"P","Q","R","S","T",
			"U","V","W","X","Y",
			"Z"};


	public static Set<String> getColTitleSet(int beginIndex, int endIndex) {
		int length = endIndex - beginIndex;
		String[] result = new String[length];
		System.arraycopy(colTitleArray, beginIndex,result, 0, length);
		return new HashSet<>(Arrays.asList(result));
	}

	public static void main(String[] args) {
	}
}
