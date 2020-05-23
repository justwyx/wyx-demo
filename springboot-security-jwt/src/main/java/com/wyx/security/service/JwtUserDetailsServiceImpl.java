package com.wyx.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	/**
	 * SpringSecurity会去IOC容器中寻找实现这个接口的实现类，并将该实现类作为默认的认证类。这个类主要用于获取用户身份信息，并不需要我们去判断用户名和密码是否匹配。参照UserDetails实现的getPassword和getUsername方法。
	 */
	@Override
	public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
		return Optional.ofNullable(userService.getUser(account))
				.orElseThrow(() -> new UsernameNotFoundException(String.format("No user found with username '%s'.", account)));
	}
}
