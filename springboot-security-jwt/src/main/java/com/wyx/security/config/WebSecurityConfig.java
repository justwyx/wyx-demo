package com.wyx.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启security方法级别权限控制注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
	@Autowired
	private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
	@Autowired
	private RestAccessDeniedHandler restAccessDeniedHandler;
	@Autowired
	private PasswordEncoder passwordEncoder;

//	@Autowired
//	private SimpleUrlAuthenticationSuccessHandler successHandler;
//
//	@Autowired
//	private SimpleUrlAuthenticationFailureHandler failureHandler;

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}


//	/**
//	 * 访问路径
//	 */
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth
//				.inMemoryAuthentication()
//				.withUser("admin1") // 管理员，同事具有 ADMIN,USER权限，可以访问所有资源
//				.password("admin1")
//				.roles("ADMIN", "USER")
//				.and()
//				.withUser("user1").password("user1") // 普通用户，只能访问 /product/**
//				.roles("USER");
//	}

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity
				.csrf().disable()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

				.and()
				.authorizeRequests()
				//这里的参数为不需要认证的uri,**代表匹配多级路径，*代表匹配一级路径，#代表一个字符....
				.antMatchers(
						"/api/user/login"
				).permitAll()
				//这里表示该路径需要管理员角色
				.antMatchers("/api/user/login/log").hasAnyAuthority("/api/user/login/log")
				.antMatchers("/api/user/login").hasAnyAuthority("/api/user/login")
				.antMatchers("/api/user/delete").hasAnyAuthority("/api/user/delete")
				.anyRequest().authenticated()
				.and()
				.headers().cacheControl();


		//添加认证过滤
		httpSecurity.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
		//添加权限不足及验证失败处理器
		httpSecurity.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);

	}


	// 这个为SpringSecurity的加密类
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}