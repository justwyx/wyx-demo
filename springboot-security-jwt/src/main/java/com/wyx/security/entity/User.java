package com.wyx.security.entity;

import com.wyx.common.enums.EnableEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * UserDetails接口是SpringSecurity框架用于认证授权的一个载体，只有实现了这个接口的类才能被SpringSecurity验证，
 */
@Accessors(chain = true)
@Data
public class User implements UserDetails {
	private String name;

	private String phone;

	private String account;

	private String password;

	private EnableEnum status;

	private List<String>  authorizationUrlList;




	/**
	 * 获取用户权限
	 * 此处做url级别的拦截
	 * @return
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auths = new ArrayList<>();
		if (!CollectionUtils.isEmpty(authorizationUrlList)) {
			for (String url : authorizationUrlList) {
				auths.add(new SimpleGrantedAuthority(url));
			}
		}
		return auths;
	}

	/**
	 * 获取用户名
	 * @return
	 */
	@Override
	public String getUsername() {
		return account;
	}

	//用户账号是否过期
	@Override
	public boolean isAccountNonExpired() {
		return EnableEnum.ENABLE.compareTo(status) == 0;
	}

	//用户账号是否锁定
	@Override
	public boolean isAccountNonLocked() {
		return EnableEnum.ENABLE.compareTo(status) == 0;
	}

	//用户凭证是否过期
	@Override
	public boolean isCredentialsNonExpired() {
		return EnableEnum.ENABLE.compareTo(status) == 0;
	}

	//账号是否可用
	@Override
	public boolean isEnabled() {
		return EnableEnum.ENABLE.compareTo(status) == 0;
	}
}
