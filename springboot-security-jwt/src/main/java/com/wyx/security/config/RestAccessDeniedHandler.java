package com.wyx.security.config;

import com.alibaba.fastjson.JSONObject;
import com.wyx.common.model.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : 账户权限不足处理器
 * @Date : 2020/5/23
 */
@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler {


	@Override
	public void handle(HttpServletRequest httpServletRequest, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setStatus(403);
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(JSONObject.toJSONString(Result.fail("授权失败")));
	}
}
