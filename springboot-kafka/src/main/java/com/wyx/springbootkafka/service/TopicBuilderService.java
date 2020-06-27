package com.wyx.springbootkafka.service;

import org.springframework.kafka.config.TopicBuilder;
import org.springframework.stereotype.Service;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/23
 * @Date : 2020/6/23
 */
@Service
public class TopicBuilderService {



	public void create(String topic, int partitionCount, int replicaCount) {
		TopicBuilder.name(topic).partitions(partitionCount).replicas(replicaCount);
	}
}
