package com.wyx.fileanalysis.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Optional;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/27
 * @Date : 2020/5/27
 */
@Accessors(chain = true)
@Data
public class FromPageDataDTO {
	private String name;

	private Integer rowNum;

	private Integer colNum;

	private String[][] data;

	public FromPageDataDTO() {
	}

	public FromPageDataDTO(String[][] data) {
		rowNum = data.length;

		colNum = rowNum == 0? 0 : data[0].length;
//		if (rowNum == 0) {
//
//		} else {
//			colNum = Optional.ofNullable(data)
//					.map(s -> data[0])
//					.map(s -> s.length)
//					.orElseGet(() -> 0);
//		}

		this.data = data;
	}

	public FromPageDataDTO(String name, String[][] data) {
		this(data);
		this.name = name;
	}
}
