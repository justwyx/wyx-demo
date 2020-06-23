package com.wyx.springbootdemo.dto;


import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @Description : TODO 2020/6/23
 * @author : Just wyx
 * @Date : 2020/6/23
 */
public class ExternalParam {

	@NotNull(message = "merchantId为空")
	private Integer merchantId;

	@NotNull(message = "sign为空")
	private String sign;

	@NotNull(message = "param为空")
	private Map<String, String> param;


	public Integer getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(Integer merchantId) {
		this.merchantId = merchantId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Map<String, String> getParam() {
		return param;
	}

	public void setParam(Map<String, String> param) {
		this.param = param;
	}

	@Override
	public String toString() {
		return "ExternalParam{" +
				"merchantId=" + merchantId +
				", sign='" + sign + '\'' +
				", param=" + param +
				'}';
	}
}
