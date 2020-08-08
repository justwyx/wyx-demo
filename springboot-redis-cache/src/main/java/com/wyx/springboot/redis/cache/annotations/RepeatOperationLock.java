package com.wyx.springboot.redis.cache.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description : 重复提交锁
 * @author : Just wyx
 * @Date : 2020/8/8
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RepeatOperationLock {

	/**
	 * 锁时长，默认3000ms
	 */
	long timeOut() default 3000;
}
