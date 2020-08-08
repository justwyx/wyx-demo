package com.wyx.springboot.redis.cache.entity;

import java.util.Date;

public class Config {
	/**
	 * 自增_id
	 */
	private Integer id;

	/**
	 * 配置key
	 */
	private String keywork;

	/**
	 * 配置value
	 */
	private String value;

	/**
	 * 创建人
	 */
	private String createName;

	/**
	 * 更新人
	 */
	private String modifyName;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 更新时间
	 */
	private Date modifyTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKeywork() {
		return keywork;
	}

	public void setKeywork(String keywork) {
		this.keywork = keywork;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCreateName() {
		return createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

	public String getModifyName() {
		return modifyName;
	}

	public void setModifyName(String modifyName) {
		this.modifyName = modifyName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}