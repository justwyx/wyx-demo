package com.wyx.springbootkafkaclients.demo1;

import org.apache.kafka.clients.consumer.ConsumerInterceptor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Just wyx
 * @Description : 消费者拦截器
 * @Date : 2020/6/26
 */
public class ConsumerInterceptorTTL implements ConsumerInterceptor<String, String> {
	private static final long EXPIRE_INTERVAL = 10 * 1000;

	/**
	 * 对超过10秒未处理的消息过滤
	 * @param records
	 * @return
	 */
	@Override
	public ConsumerRecords<String, String> onConsume(ConsumerRecords<String, String> records) {
		long now = System.currentTimeMillis();
		Map<TopicPartition, List<ConsumerRecord<String, String>>> newRecords = new HashMap<>();
		for (TopicPartition topicPartition : records.partitions()) {
			List<ConsumerRecord<String, String>> tpRecords = records.records(topicPartition);
			List<ConsumerRecord<String, String>> newTpRecords = new ArrayList<>();
			for (ConsumerRecord<String, String> tpRecord : tpRecords) {
				if (now - tpRecord.timestamp() < EXPIRE_INTERVAL) {
					newTpRecords.add(tpRecord);
				}
			}
			if (!newTpRecords.isEmpty()) {
				newRecords.put(topicPartition, newTpRecords);
			}
		}

		return new ConsumerRecords<>(newRecords);
	}

	/**
	 * 打印 offset
	 * @param offsets
	 */
	@Override
	public void onCommit(Map<TopicPartition, OffsetAndMetadata> offsets) {
		offsets.forEach(((topicPartition, offsetAndMetadata) ->
				System.out.println(topicPartition + ":" + offsetAndMetadata.offset())
				));
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> configs) {

	}
}
