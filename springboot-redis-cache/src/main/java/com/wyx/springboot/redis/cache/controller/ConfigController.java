package com.wyx.springboot.redis.cache.controller;

import com.wyx.springboot.redis.cache.annotations.RepeatOperationLock;
import com.wyx.springboot.redis.cache.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/6
 * @Date : 2020/8/6
 */
@RequestMapping("/config")
@RestController
public class ConfigController {

	@Autowired
	private ConfigService configService;

	@RepeatOperationLock(timeOut = 10000)
	@RequestMapping("/key/{key}")
	public String getConfigByKey(@PathVariable("key") String key) {
		return configService.getByKey(key);
	}
}
