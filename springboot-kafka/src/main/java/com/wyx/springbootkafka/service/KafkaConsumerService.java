package com.wyx.springbootkafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


/**
 * @Description : kafka消费者
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@Service
public class KafkaConsumerService {

	private final Logger logger= LoggerFactory.getLogger(this.getClass());

	//声明consumerID为demo，监听topicName为topic.quick.demo的Topic
	@KafkaListener(id = "demo", topics = "topic.test")
	public void listen(ConsumerRecord<?, ?> record) {
		logger.info("ConsumerRecord:" + record.toString());
	}
}
