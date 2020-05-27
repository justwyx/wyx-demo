package com.wyx.fileanalysis.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description : 格式文件-dto
 * @author : Just wyx
 * @Date : 2020/5/27
 */
@Accessors(chain = true)
@Data
public class FormFileDTO {
	private String fileName;
	private Integer pageNum;
	private List<FromPageDataDTO> pageDataList;
}
