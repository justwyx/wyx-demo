package com.wyx.common.model;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@Accessors(chain = true)
@Data
public class Result<T> {
	private String code;

	private String message;

	private T data;

	public Result() {
	}

	public Result(Result param) {
		this.code = param.getCode();
		this.message = param.getMessage();
	}

	public static<T> Result<T> success(T data){
		return new Result<T>()
				.setCode("OK")
				.setMessage("成功")
				.setData(data);
	}

	public static Result fail(String message){
		return new Result()
				.setCode("FAIL")
				.setMessage(message);
	}
}
