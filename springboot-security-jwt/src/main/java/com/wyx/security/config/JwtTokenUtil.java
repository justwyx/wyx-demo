package com.wyx.security.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@Component
public class JwtTokenUtil{

	/**
	 * 密钥
	 */
	private static final String secret = "lkhouhubkljgpihojblkjboiboihu9u";

	/**
	 * 从数据声明生成令牌
	 *
	 * @param claims 数据声明
	 * @return 令牌
	 */
	public static String generateToken(Map<String, Object> claims) {
		//设置token的有效期为24*7小时,也就是一周
		Date expirationDate = new Date(System.currentTimeMillis() +60*60*24*7 * 1000);
		return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	/**
	 * 从令牌中获取数据声明
	 *
	 * @param token 令牌
	 * @return 数据声明
	 */
	public static Claims getClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}

	/**
	 * 生成令牌
	 *
	 * @param userDetails 用户
	 * @return 令牌
	 */
	public static String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>(2);
		claims.put("sub", userDetails.getUsername());
		claims.put("created", new Date());
		return generateToken(claims);
	}

	/**
	 * 从令牌中获取用户名
	 *
	 * @param token 令牌
	 * @return 用户名
	 */
	public static String getUsernameFromToken(String token) {
		String username;
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		return username;
	}

	/**
	 * 判断令牌是否过期
	 *
	 * @param token 令牌
	 * @return 是否过期
	 */
	public static Boolean isTokenExpired(String token) {
		try {
			Claims claims = getClaimsFromToken(token);
			Date expiration = claims.getExpiration();
			return expiration.before(new Date());
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 刷新令牌
	 *
	 * @param token 原令牌
	 * @return 新令牌
	 */
	public static String refreshToken(String token) {
		String refreshedToken;
		try {
			Claims claims = getClaimsFromToken(token);
			claims.put("created", new Date());
			refreshedToken = generateToken(claims);
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	/**
	 * 验证令牌
	 *
	 * @param token       令牌
	 * @param userDetails 用户
	 * @return 是否有效
	 */
	public static Boolean validateToken(String token, UserDetails userDetails) {
		String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	public static void main(String[] args) {
		System.out.println(BCrypt.hashpw("admin", "admin"));
	}
}
