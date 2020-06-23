package com.wyx.springbootdemo.util;

import org.apache.commons.lang3.StringUtils;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/23
 * @Date : 2020/6/23
 */
public class SignUtil {

	/**
	 * 获取sign
	 * @param paramMap 入参Map
	 * @param key key值
	 * @return
	 */
	public static String getSing(Map<String, String> paramMap, String key) {
		return getMD5Str(getStringSignTemp(paramMap, key)).toUpperCase();
	}


	private static String getStringSignTemp(Map<String, String> paramMap, String key) {
		if (paramMap == null) {
			return "";
		}

		String[] paramArray = new String[paramMap.size()];
		int index = 0;
		for (Map.Entry<String, String> stringStringEntry : paramMap.entrySet()) {
			paramArray[index++] = stringStringEntry.getKey();
		}

		Arrays.sort(paramArray);

		StringBuilder sb = new StringBuilder();
		String value;
		for (String paramKey : paramArray) {
			value = paramMap.get(paramKey);
			if (StringUtils.isBlank(value)) {
				continue;
			}
			sb.append(paramKey).append("=").append(value).append("&");
		}
		return sb.append("key=").append(key).toString();
	}

	private static String getMD5Str(String str) {
		byte[] digest;
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			digest = md5.digest(str.getBytes("utf-8"));
		} catch (Exception e) {
			return "";
		}
		// 16是表示转换为16进制数
		return new BigInteger(1, digest).toString(16);
	}


	public static void main(String[] args) {
		Map<String, String> param = new HashMap<>();
		param.put("bb", "2");
		param.put("aa", "1");
		param.put("cc", "3");

		System.out.println("sign:" + getSing(param, "key"));
	}
}
