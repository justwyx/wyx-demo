package com.wyx.springbootkafka.model;

import lombok.Data;

/**
 * @Description : 生成topic
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@Data
public class CreateTopicParam {

	private String topic;

	private Integer numPartitions;

	private Short replicationFactor;

	@Override
	public String toString() {
		return "CreateTopicParam{" +
				"topic='" + topic + '\'' +
				", numPartitions=" + numPartitions +
				", replicationFactor=" + replicationFactor +
				'}';
	}
}
