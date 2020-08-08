package com.wyx.springboot.redis.cache.mapper;


import com.wyx.springboot.redis.cache.entity.Config;

public interface ConfigMapper {
    Config selectByPrimaryKey(Integer id);

    Config getBykeywork(String key);

    int insert(Config record);

    int insertSelective(Config record);

    int updateByPrimaryKeySelective(Config record);

    int updateByPrimaryKey(Config record);
}