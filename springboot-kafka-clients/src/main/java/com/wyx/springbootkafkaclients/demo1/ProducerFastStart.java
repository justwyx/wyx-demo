package com.wyx.springbootkafkaclients.demo1;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/25
 * @Date : 2020/6/25
 */
public class ProducerFastStart {
	public static final String brokerList = "localhost:9092";
	public static final String topic = "topic-demo";

	private static final long EXPIRE_INTERVAL = 11 * 1000;


	public static Properties initConfig() {
		Properties props = new Properties();
		/** 必要参数配置 */
		// kafka集群服务地址
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerList);
		// key 序列化方式
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// value 序列化方式
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());


		/** 需要了解一下的参数配置 */
		/**
		 * 客户端id,默认值为: "", 不设置则会自动生成一个非空字符串，内容形式如"producer-1" "producer-2"
		 */
		props.put(ProducerConfig.CLIENT_ID_CONFIG, "producer.client.id.demo");
		/**
		 * retries:对于可重试异常, 如果配置了retries参数，则在规定的次数内自行恢复，就不会抛出异常,默认:0,不做任何重试
		 * retry.backoff.ms:用来设定两次重试之间的间隔，默认值为100
 		 */
		props.put(ProducerConfig.RETRIES_CONFIG, 10);
		props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100);

		/**
		 * 指定分区中必需要有多少个副本收到这条消息，默认为: "1"
		 * 注意：acks参数配置的值为字符串类型，应采用 "1" 而不是 1,否则出执行异常: org.apache.kafka.common.config.ConfigException: Invalid value 1 for configuration acks: Expected value to be a string, but it was a java.lang.Integer
		 * 0:生产者发送消息，不需要等待任何服务端响应
		 * 1:生产者发送消息后，只需要leader成功写入消息即为成功
		 * -1 或 all，生产者发送消息后，需要等待ISR中所有副本都成功写入消息才算成功
		 */
		props.put(ProducerConfig.ACKS_CONFIG, "1");
		/**
		 * 限制生产者客户端能发送的消息最大值，默认值为: 1048576B,即1MB
		 * 不建议使用！
		 * 因为还涉及到一些其它参数的联动
		 * 如果有需要优先从拆分发送消息来处理
		 */
//		props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 1048576);
		/**
		 * 指定消息的压缩方式，默认值为"none",即不压缩
		 * 还可以配置为"gzip" "snappy" "lz4"
		 * 一般不推荐使用
		 */
//		props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");
		/**
		 * 指定在多久之后关闭闲置连接，默认值为: 540000(ms),即9分钟
		 */
		props.put(ProducerConfig.CONNECTIONS_MAX_IDLE_MS_CONFIG, 540000);
		/**
		 * 指定生产者发送 ProducerBatch 之前等待更多消息加入的时间，默认值为: 0
		 * 生产都会在 ProducerBatch 填满之前或等待时间超过 linger.ms 值时发送出去
		 * 增大这个值会增加消息的延迟，但同时能提升一定的吞吐量
		 */
		props.put(ProducerConfig.LINGER_MS_CONFIG, 0);
		/**
		 * 设置 socket接收消息缓冲区(SO_RECBUF)的大小，默认值为: 32768(B),即 32KB
		 * 如果设置为 -1,则使用操作系统的默认值,如果 producer 与 kafka 处于不同的机房，则可适当调大这个参数值
		 */
		props.put(ProducerConfig.RECEIVE_BUFFER_CONFIG, 32768);
		/**
		 * 设置socket发送消息缓冲区(SO_SNDBUF)的大小,默认值为:131072(B),即 128KB
		 * 如果设置为 -1,则使用操作系统的默认值
		 */
		props.put(ProducerConfig.SEND_BUFFER_CONFIG, 131072);
		/**
		 * 配置product等待请求响应的最大时间， 默认值为: 30000(ms)
		 */
		props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, 30000);

		return props;
	}

	public static void main(String[] args) {
		// 配置生产者客户端参数并创建kafkaProducer实例
		KafkaProducer<String, String> producer = new KafkaProducer<>(initConfig());
		// 构建所需要发送的消息
		ProducerRecord<String, String> record = new ProducerRecord<>(topic, "hello kafka!");

		// 发送消息
		try {
			/**
			 * fire-and-forget(发后即忘)
 			 */
			producer.send(record);

			/**
			 * sync
			 */
			RecordMetadata recordMetadataSync = producer.send(record).get();
			System.out.println(recordMetadataSync.toString());

			/**
			 * async
			 */
			producer.send(record, (metadata, exception) -> {
				/**
				 * metadata exception 两参数互斥
				 * 成功: metadata is not null,exception is null
				 * 失败: metadata is null,exception is not null
 				 */

				if (exception != null) {
					// 只做简单的日志打印,生产中不要这样做
					exception.printStackTrace();
				} else {
					System.out.println(metadata.toString());
				}
			});

			/**
			 * 测试拦截器
			 */
			ProducerRecord<String, String> record4 = new ProducerRecord<>(topic, 0,System.currentTimeMillis() - EXPIRE_INTERVAL, null, "first-expire-data");
			producer.send(record4).get();

			ProducerRecord<String, String> record5 = new ProducerRecord<>(topic, 0,System.currentTimeMillis(), null, "normal-data");
			producer.send(record5).get();

			ProducerRecord<String, String> record6 = new ProducerRecord<>(topic, 0,System.currentTimeMillis() - EXPIRE_INTERVAL, null, "last-expire-data");
			producer.send(record6).get();

		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}finally {
			// 关闭生产者, 阻塞等待之前所有发送请求完成后再关闭
			producer.close();
		}
	}
}
