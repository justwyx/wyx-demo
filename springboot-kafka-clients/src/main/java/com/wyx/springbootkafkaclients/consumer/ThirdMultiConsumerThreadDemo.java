package com.wyx.springbootkafkaclients.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author : Just wyx
 * @Description : 线程封装，每个线程实例化一个 KafkaConsumer 对象
 * @Date : 2020/6/27
 */
public class ThirdMultiConsumerThreadDemo {
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
		int i = Runtime.getRuntime().availableProcessors();
		KafkaConsumerThread consumerThread = new KafkaConsumerThread(properties, topic, i);
		consumerThread.start();
	}

	public static class KafkaConsumerThread extends Thread{
		private KafkaConsumer<String, String> kafkaConsumer;
		private ExecutorService executorService;
		private int threadNumber;

		public KafkaConsumerThread(Properties props, String topic, int threadNumber) {
			this.kafkaConsumer = new KafkaConsumer<>(props);
			this.kafkaConsumer.subscribe(Collections.singletonList(topic));
			this.threadNumber = threadNumber;
			executorService = new ThreadPoolExecutor(this.threadNumber, this.threadNumber, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(1000), new ThreadPoolExecutor.CallerRunsPolicy());
		}

		@Override
		public void run(){
			while (true) {
				ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(100));
				if (!records.isEmpty()) {
					executorService.submit(new RecordsHandler(records));
				}
			}
		}
	}

	public static class RecordsHandler extends Thread {
		public final ConsumerRecords<String, String> records;

		public RecordsHandler(ConsumerRecords<String, String> records) {
			this.records = records;
		}

		@Override
		public void run() {
			System.out.println(records.toString());
		}
	}
}
