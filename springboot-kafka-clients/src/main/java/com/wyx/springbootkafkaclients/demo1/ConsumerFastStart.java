package com.wyx.springbootkafkaclients.demo1;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/25
 * @Date : 2020/6/25
 */
public class ConsumerFastStart {
	public static final String brokerList = "localhost:9092";
	public static final String topic = "topic-demo";
	public static final String groupId = "group.demo";


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

		/** 需要了解一下的参数配置 */
		/**
		 * ##重要##
		 * 配置 consumer 在一次拉取请求中拉取的最大消息数，默认值:500(条)
		 * 也可以适当调大该值提升一定的消费速度
		 */
		props.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 500);
		/**
		 * ##重要##
		 * 配置指定拉取消息线程最长空闲时间，若超过这个时间间隔还没发起poll操作
		 * 则消费者组认为消费者已离开，将进行再均衡操作
		 * 如果该值太小，消息还未处理完，则会造成循环重复消费
		 */
		props.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, 300000);
		/**
		 * 客户端id,默认值为: "", 不设置则会自动生成一个非空字符串，内容形式如"consumer-1" "consumer-2"
		 */
		props.put(ConsumerConfig.CLIENT_ID_CONFIG, "consumer.client.id.demo");
		/**
		 * 指定提交方式,默认值为: true(自动提交)
		 */
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		/**
		 * 自动提交消息的间格时间，默认为5000(ms)
		 */
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 5000);
		/**
		 * 消费者拦截器，默认为:""
		 */
		props.put(ConsumerConfig.INTERCEPTOR_CLASSES_CONFIG, ConsumerInterceptorTTL.class.getName());
		/**
		 * 配置consumer在一次拉取请求中能从kafka中拉取的最小数据量,默认值为1(B)
		 * 适当调高能增加吞吐量，但会造成额外的延迟
		 */
//		props.put(ConsumerConfig.FETCH_MIN_BYTES_CONFIG, 1);
		/**
		 * 配置consumer在一次拉取请求中能从kafka中拉取的最大数据量,默认值为52428800(B)，即50MB
		 * 一般不设置
		 */
//		props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, 52428800);
		/**
		 * 与 fetch.min.bytes 有关，当kafka中消息没有满足 fetch.min.bytes 最小值的要求
		 * 那么最终会等待500ms
		 * 默认值为: 500(ms)
		 */
		props.put(ConsumerConfig.FETCH_MAX_WAIT_MS_CONFIG, 500);
		/**
		 * 配置每个分区拉了的数据量最大值，默认值:1048576(B)，即 1MB
		 */
		props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, 1048576);



		/**
		 * 指定多久关闭闲置连接，默认值:540000(ms)，即9分钟
		 */
		props.put(ConsumerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 540000);
		/**
		 * 默认值为:true
		 * 如果设置为true,则只能使用subscribe(Collection)的方式，而不能用subscribe(Pattern)的方式来订阅主题
		 * false没有些限制
		 */
		props.put(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG, true);
		/**
		 * 设置 socket接收消息缓冲区(SO_RECBUF)的大小，默认值为: 65536(B),即 64KB
		 * 如果设置为 -1,则使用操作系统的默认值,如果 producer 与 kafka 处于不同的机房，则可适当调大这个参数值
		 */
		props.put(ProducerConfig.RECEIVE_BUFFER_CONFIG, 65536);
		/**
		 * 设置socket发送消息缓冲区(SO_SNDBUF)的大小,默认值为:131072(B),即 128KB
		 * 如果设置为 -1,则使用操作系统的默认值
		 */
		props.put(ProducerConfig.SEND_BUFFER_CONFIG, 131072);
		/**
		 * 配置consumer等待请求响应的最大时间，默认30000(ms),即30s
		 */
		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30 * 1000);
		/**
		 * 配置元数据过期时间，默认值为5分钟
		 */
		props.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, 5 * 60 * 1000);
		/**
		 * 配置尝试发送失败的请求到指定主题分区之前的等待时间，默认100(ms)
		 * ，避免在某些故障的情况下频繁的重复发送
		 */
		props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100);






		return props;
	}


	public static void main(String[] args) {
		// 创建消费者客户端实例
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(initConfig());
		// 订阅主题
		consumer.subscribe(Collections.singletonList(topic));

		/**
		 * 指定消费某一个分区
		 */
//		consumer.assign(Arrays.asList(new TopicPartition(topic, 1)));
		/**
		 * 获取所有分区并消费
		 */
//		List<TopicPartition> topicPartitionList = new ArrayList<>();
//		List<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
//		if (partitionInfos != null) {
//			for (PartitionInfo partitionInfo : partitionInfos) {
//				topicPartitionList.add(new TopicPartition(partitionInfo.topic(), partitionInfo.partition()));
//			}
//		}
//		consumer.assign(topicPartitionList);

//		 循环消费消息
		while (true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
			for (ConsumerRecord<String, String> record : records) {
				System.out.println(record.toString());
			}

			// 同步提交
//			consumer.commitSync();

			// 异步提交
//			consumer.commitAsync();
		}

		/**
		 * commitSync
		 * 带参数的同步位移提交，消费一条，提交一条
		 * 实际场景比较少，性能很低
		 */
//		while (true) {
//			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//			for (ConsumerRecord<String, String> record : records) {
//
//				long offset = record.offset();
//				TopicPartition topicPartition = new TopicPartition(record.topic(), record.partition());
//				consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(offset + 1)));
//			}
//		}

		/**
		 * commitSync
		 * 按分区粒度提交
		 */
//		while (true) {
//			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
//			for (TopicPartition topicPartition : records.partitions()) {
//				List<ConsumerRecord<String, String>> recordList = records.records(topicPartition);
//
//				for (ConsumerRecord<String, String> stringStringConsumerRecord : recordList) {
//					System.out.println(stringStringConsumerRecord.toString());
//				}
//				long offset = recordList.get(recordList.size() - 1).offset();
//				consumer.commitSync(Collections.singletonMap(topicPartition, new OffsetAndMetadata(offset + 1)));
//			}
//		}

	}

}
