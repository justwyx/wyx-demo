package com.wyx.security.service;

import com.wyx.common.enums.EnableEnum;
import com.wyx.security.config.JwtTokenUtil;
import com.wyx.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@Service
public class UserService {

//	@Autowired
//	private PasswordEncoder passwordEncoder;
	@Autowired
	private AuthenticationManager authenticationManager;

	public String login(String phone, String password) {
		// 将用户名和密码生成Token
		UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(phone, password);
		// 调用该方法时SpringSecurity会去调用JwtUserDetailsServiceImpl 进行验证
		Authentication authentication = authenticationManager.authenticate(upToken);
		// 存储当前认证信息
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User userDetails = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return JwtTokenUtil.generateToken(userDetails);
	}

	/**
	 * @Description : 不连接数据库
	 * @param account 入参
	 * @Author : Just wyx
	 * @Date : 12:34 2020/5/23
	 * @return : com.wyx.security.entity.User
	 */
	public User getUser(String account) {
		if (StringUtils.isEmpty(account) || "admin".equals(account)) {
			return new User()
					.setAccount("admin")
					.setPassword("$2a$10$fU9wdb7euPg.hcxrr1y6GOZuu7UpIW2QGncZ/yKoDHPA8g3NvhxcS")
					.setName("testName")
					.setAuthorizationUrlList(Arrays.asList("/api/user/login/log", "/api/user/login"))
					.setStatus(EnableEnum.ENABLE);
		}
		return null;
	}

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		System.out.println(passwordEncoder.encode("admin"));
	}
}
