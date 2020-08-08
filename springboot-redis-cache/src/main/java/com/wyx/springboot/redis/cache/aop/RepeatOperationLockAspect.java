package com.wyx.springboot.redis.cache.aop;

import com.alibaba.fastjson.JSONObject;
import com.wyx.springboot.redis.cache.annotations.RepeatOperationLock;
import com.wyx.springboot.redis.cache.utils.RedisUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @Description : 重复提交锁切面
 * @author : Just wyx
 * @Date : 2020/8/8
 */
@Aspect
@Component
public class RepeatOperationLockAspect {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private final static String REPEAT_OPERATION_LOCK_PREFIX = "repeat:operation:lock:";

	@Autowired
	private RedisUtil redisUtil;

	@Pointcut("@annotation(com.wyx.springboot.redis.cache.annotations.RepeatOperationLock)")
	public void repeatOperationLockAspect() {
	}

	@Before("repeatOperationLockAspect()")
	public void setLock(JoinPoint point) throws Exception {
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		RepeatOperationLock repeatOperationLock = method.getAnnotation(RepeatOperationLock.class);
		if (Objects.isNull(repeatOperationLock)) {
			return;
		}
		long timeOut = repeatOperationLock.timeOut();

		String methodName = method.getName();
		Object[] args = point.getArgs();
		String paramJsonString = JSONObject.toJSONString(args);
		System.out.println("paramJsonString:" + paramJsonString);

		String key = REPEAT_OPERATION_LOCK_PREFIX + methodName + ":" + getDigest(paramJsonString);
		System.out.println("kye:" + key);
		if (!redisUtil.setNx(key, paramJsonString, timeOut)) {
			throw new Exception();
		}
	}
	private static String getDigest(String plainText) {
		return Base64.encodeBase64String(DigestUtils.md5Hex(plainText).getBytes());
	}

}
