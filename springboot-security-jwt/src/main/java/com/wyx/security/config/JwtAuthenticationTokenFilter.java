package com.wyx.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
		String authHeader = request.getHeader("token");
		//该字符串作为Authorization请求头的值的前缀
		String tokenHead = "token-";
		if (authHeader != null && authHeader.startsWith(tokenHead)) {
			String authToken = authHeader.substring(tokenHead.length());
			//从token中获取userId
			String userId = JwtTokenUtil.getUsernameFromToken(authToken);
			if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				//调用UserDetailsService的认证方法(JwtUserDetailsServiceImpl实现类)
				UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
				//验证token是否正确
				if (JwtTokenUtil.validateToken(authToken, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					//将获取到的用户身份信息放到SecurityContextHolder中，这个类是为了在线程中保存当前用户的身份信息
					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
		} else {
//			log.info("没有获取到token");
		}
		chain.doFilter(request, response);
	}
}
