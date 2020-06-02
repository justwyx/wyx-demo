package com.wyx.fileanalysis.model;

import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

/**
 * @Description : 格式文件-dto
 * @author : Just wyx
 * @Date : 2020/5/27
 */
@Accessors(chain = true)
@Data
public class FormFileDTO {

	/**
	 * 文件名
	 */
	private String fileName;

	/**
	 * 总页数
	 */
	private Integer pageNum;

	/**
	 * 页内容
	 */
	private List<FromPageDataDTO> pageDataList;

	public FormFileDTO() {
	}

	public FormFileDTO(String fileName) {
		this.fileName = fileName;
		this.pageNum = 0;
		this.pageDataList = Lists.newArrayList();
	}

	public void addPageNum(){
		pageNum += 1;
	}
}
