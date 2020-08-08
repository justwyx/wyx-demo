package com.wyx.springboot.redis.cache.service.impl;

import com.wyx.springboot.redis.cache.entity.Config;
import com.wyx.springboot.redis.cache.mapper.ConfigMapper;
import com.wyx.springboot.redis.cache.service.ConfigService;
import com.wyx.springboot.redis.cache.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author : Just wyx
 * @Description : TODO 2020/8/6
 * @Date : 2020/8/6
 */
@Service
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigMapper configMapper;
	@Autowired
	private RedisUtil redisUtil;


	@Override
	public String getByKey(String key) {
		String redisKey = "config:" + key;
		String value = (String) redisUtil.get(redisKey);
		if (!StringUtils.isEmpty(value)) {
			return value;
		}
		Config config = configMapper.getBykeywork(key);
		value = config == null ? null : config.getValue();
		if (StringUtils.isEmpty(value)) {
			return value;
		}

		redisUtil.set(redisKey, value);
		return value;
	}



}
