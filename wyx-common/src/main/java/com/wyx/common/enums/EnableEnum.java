package com.wyx.common.enums;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
public enum EnableEnum {
	DISABLE(0, "禁用"),
	ENABLE(1, "启用");



	EnableEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	private int code;
	private String desc;

}
