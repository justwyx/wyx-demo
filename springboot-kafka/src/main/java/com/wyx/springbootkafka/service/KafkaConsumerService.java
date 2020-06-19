package com.wyx.springbootkafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * @Description : kafka消费者
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@Service
public class KafkaConsumerService {

	private final Logger logger= LoggerFactory.getLogger(this.getClass());

	@KafkaListener(topics = "topic.test")
	public void listen(ConsumerRecord<?, ?> record) throws InterruptedException {
		Thread.sleep(1000);
		logger.info("ConsumerRecord:" + record.toString());
	}

	//声明consumerID为demo，监听topicName为topic.quick.demo的Topic
//	@KafkaListener(topics = "topic.test", containerFactory = "ackManualImmediateContainerFactory")
//	@KafkaListener(topics = "topic.test")
//	public void listen(ConsumerRecord<?, ?> record, Acknowledgment ack) throws InterruptedException {
//		Thread.sleep(1000);
//		logger.info("ConsumerRecord:" + record.toString());
//		ack.acknowledge();
//	}

//	@KafkaListener(containerFactory = "ackManualContainerFactory",
//			topicPartitions = {
//					@TopicPartition(topic = "topic.test",
//							partitionOffsets = @PartitionOffset(partition = "0",initialOffset = "10"))
//			})
//	public void listen(List<ConsumerRecord<?, ?>> record, Acknowledgment ack) throws InterruptedException {
//		logger.info("ConsumerRecord:" + record.toString());
//		Thread.sleep(3000);
//
//
//		ack.acknowledge();
//	}
}
