package com.wyx.springbootmongodb.dto;

import com.wyx.springbootmongodb.enhance.QueryMust;

import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/15
 * @Date : 2020/7/15
 */

public class QueryDTO {
	@QueryMust(field = "id")
	private List<Integer> idList;

	@QueryMust(field="classificationId")
	private List<Integer> classificationId;

	@QueryMust(field="brandId")
	private List<Integer> brandId;

	@QueryMust(field="seriesId")
	private List<Integer> seriesId;

	private Long skip;
	private Integer limit;


	public List<Integer> getIdList() {
		return idList;
	}

	public void setIdList(List<Integer> idList) {
		this.idList = idList;
	}

	public List<Integer> getClassificationId() {
		return classificationId;
	}

	public void setClassificationId(List<Integer> classificationId) {
		this.classificationId = classificationId;
	}

	public List<Integer> getBrandId() {
		return brandId;
	}

	public void setBrandId(List<Integer> brandId) {
		this.brandId = brandId;
	}

	public List<Integer> getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(List<Integer> seriesId) {
		this.seriesId = seriesId;
	}

	public Long getSkip() {
		return skip;
	}

	public void setSkip(Long skip) {
		this.skip = skip;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}
}
