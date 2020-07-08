package com.wyx.springbootmongodb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/7
 * @Date : 2020/7/7
 */
@Service
public class MongodbService {
	private static final Logger logger = LoggerFactory.getLogger(MongodbService.class);

	@Autowired
	private MongoTemplate mongoTemplate;


	/**
	 *
	 * @param objectToSave
	 * @param <T>
	 * @return
	 */
	public <T> T save(T objectToSave) {
		return mongoTemplate.insert(objectToSave);
	}
	public <T> T save(T objectToSave, String collectionName) {
		return mongoTemplate.save(objectToSave, collectionName);
	}

}
