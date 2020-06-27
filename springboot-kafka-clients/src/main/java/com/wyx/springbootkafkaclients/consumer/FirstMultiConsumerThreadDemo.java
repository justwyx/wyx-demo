package com.wyx.springbootkafkaclients.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author : Just wyx
 * @Description : 线程封装，每个线程实例化一个 KafkaConsumer 对象
 * @Date : 2020/6/27
 */
public class FirstMultiConsumerThreadDemo {
	public static final String brokerList = "localhost:9092";
	public static final String topic = "topic-demo";
	public static final String groupId = "group.demo";

	private static final long EXPIRE_INTERVAL = 11 * 1000;


	public static Properties initConfig() {
		Properties props = new Properties();
		/** 必要参数配置 */
		// kafka集群服务地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
		// 消费者组id
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		// key 反序列方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
		// value 反序列方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());


		return props;
	}

	public static void main(String[] args) {
		Properties properties = initConfig();
		int consumerThreadNum = 3;
		for (int i = 0; i < consumerThreadNum; i++) {
			new KafkaConsumerThread(properties, topic).start();
		}
	}

	public static class KafkaConsumerThread extends Thread{
		private KafkaConsumer<String, String> kafkaConsumer;

		public KafkaConsumerThread(Properties props, String topic) {
			this.kafkaConsumer = new KafkaConsumer<>(props);
			this.kafkaConsumer.subscribe(Arrays.asList(topic));
		}

		@Override
		public void run(){
			while (true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record.toString());
				}
			}
		}
	}
}
