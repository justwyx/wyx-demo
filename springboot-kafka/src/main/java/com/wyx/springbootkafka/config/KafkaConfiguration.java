package com.wyx.springbootkafka.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : kafka生产消费者配置信息
 * @Date : 2020/5/25
 */
@Configuration
@EnableKafka
public class KafkaConfiguration {


	@Value("${iiasaas.kafka.bootstrap-servers}")
	private String KAFKA_BOOTSTRAP_SERVERS;
	@Value("${spring.application.name}")
	private String KAFKA_GROUP_ID;

	// 默认配置，自动提交，批量
	@Bean("kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Integer, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		// 默认自动批量提交
		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.BATCH));
		return factory;
	}

	// 自动提交，批量提交，批量监听
	@Bean("batchListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Integer, String> batchListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.BATCH));
		//设置为批量监听
		factory.setBatchListener(true);
		return factory;
	}

	// 大任务,手动提交，单条提交
	@Bean("ackBigTaskContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Integer, String> ackBigTaskContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		// 大任务消费者配制
		factory.setConsumerFactory(consumerBigTaskFactory());
		// 单条提交
		factory.getContainerProperties().setAckMode((ContainerProperties.AckMode.MANUAL_IMMEDIATE));
		return factory;
	}



	// 根据senderProps填写的参数创建生产者工厂
	@Bean
	public ProducerFactory<Integer, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(senderProps());
	}

	// kafkaTemplate实现了Kafka发送接收等功能
	@Bean
	public KafkaTemplate<Integer, String> kafkaTemplate() {
		KafkaTemplate template = new KafkaTemplate<>(producerFactory());
		return template;
	}

	// 根据consumerProps填写的参数创建消费者工厂
	@Bean
	public ConsumerFactory<Integer, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps());
	}

	// 根据consumerProps填写的参数创建消费者工厂
	@Bean
	public ConsumerFactory<Integer, String> consumerBigTaskFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerBigTaskProps());
	}

	// 消费者配置参数
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		//连接地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		//GroupID
		props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);
		//是否自动提交
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		//自动提交的频率
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
		//Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

		// poll 一次拉取数量
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 100);
		// 两次poll 处理的超时时间,超时提交offset失败,造成重复消费,新任务永远不会执行
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 10 * 60 * 1000);

		//键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		//值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	// 大任务消费者
	private Map<String, Object> consumerBigTaskProps() {
		Map<String, Object> props = new HashMap<>();
		//连接地址
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		//GroupID
		props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_GROUP_ID);
		//是否自动提交
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		//自动提交的频率
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
		//Session超时设置
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "10000");

		// poll 一次拉取数量
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1);
		// 两次poll 处理的超时时间,超时提交offset失败,造成重复消费,新任务永远不会执行
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 3 * 60 * 60 * 1000);

		//键的反序列化方式
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, IntegerDeserializer.class);
		//值的反序列化方式
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	// 生产者配置
	private Map<String, Object> senderProps (){
		Map<String, Object> props = new HashMap<>();
		//连接地址
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_BOOTSTRAP_SERVERS);
		//重试，0为不启用重试机制
		props.put(ProducerConfig.RETRIES_CONFIG, 1);
		//控制批处理大小，单位为字节
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		//批量发送，延迟为1毫秒，启用该功能能有效减少生产者发送消息次数，从而提高并发量
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		//生产者可以使用的总内存字节来缓冲等待发送到服务器的记录
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024000);
		//键的序列化方式
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class);
		//值的序列化方式
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}
}
