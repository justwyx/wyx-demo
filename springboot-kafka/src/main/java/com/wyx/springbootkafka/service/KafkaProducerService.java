package com.wyx.springbootkafka.service;

import org.junit.Test;
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
	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Test
	public void sendTest(String value) {
		kafkaTemplate.send("topic.test", value);
	}
}
