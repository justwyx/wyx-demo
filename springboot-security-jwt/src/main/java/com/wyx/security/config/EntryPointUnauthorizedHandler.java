package com.wyx.security.config;

import com.alibaba.fastjson.JSONObject;
import com.wyx.common.model.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : 账号密码验证失败处理器
 * @Date : 2020/5/23
 */
@Component
public class EntryPointUnauthorizedHandler implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(401);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSONObject.toJSONString(Result.fail("登录失败")));
	}
}
