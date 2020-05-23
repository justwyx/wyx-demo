package com.wyx.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * jwt生成并发给客户端之后，后台是不用存储的，
 * 客户端访问时会验证其签名、过期时间等再取出里面的信息（如username）
 * 然后使用该信息直接查询用户信息完成登录验证
 */
@SpringBootApplication
public class SecurityJwtApplication {

	/**
	 * JWT使用场景无状态的分布式APIJWT的主要优势在于使用无状态、可扩展的方式处理应用中的用户会话。
	 * 服务端可以通过内嵌的声明信息，很容易地获取用户的会话信息，
	 * 而不需要去访问用户或会话的数据库。在一个分布式的面向服务的框架中，这一点非常有用。
	 * 但是，如果系统中需要使用黑名单实现长期有效的token刷新机制，这种无状态的优势就不明显了。
	 *
	 * 优势
	 * 快速开发不需要cookie
	 * JSON在移动端的广泛应用
	 * 不依赖于社交登录
	 * 相对简单的概念理解
	 *
	 * 限制
	 * Token有长度限制
	 * Token不能撤销
	 * 需要token有失效时间限制
	 */
	public static void main(String[] args) {
		SpringApplication.run(SecurityJwtApplication.class, args);
	}

}
