package com.wyx.springbootkafka.service;

import com.alibaba.fastjson.JSONObject;
import com.wyx.springbootkafka.model.TopicMessageParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @Description : kafka生产者
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@Service
public class KafkaProducerService {
	private final Logger logger= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private KafkaTemplate kafkaTemplate;

	public void sendMessage(TopicMessageParam param) {
		logger.info("sendMessage:{}", JSONObject.toJSONString(param));
		kafkaTemplate.send(param.getTopic(), param.getMessage());
	}

	public void sendMessage(String topic, String str) {
		logger.info("sendMessage--topic:{},sendMessage:{}", topic, str);
		kafkaTemplate.send(topic, str);
	}
}
