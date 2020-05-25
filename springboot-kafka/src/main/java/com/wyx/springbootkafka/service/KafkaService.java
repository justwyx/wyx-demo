package com.wyx.springbootkafka.service;

import com.wyx.common.model.Result;
import com.wyx.springbootkafka.model.CreateTopicParam;
import com.wyx.springbootkafka.model.TopicMessageParam;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/25
 * @Date : 2020/5/25
 */
@Service
public class KafkaService {
	private final Logger logger= LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AdminClient adminClient;

	/**
	 * (what) : 初始化topic
	 * (why) : 
	 * (how) : 
	 * @param param 入参
	 * @Author : Just wyx
	 * @Date : 18:02 2020/5/25 
	 * @return : com.wyx.common.model.Result<org.apache.kafka.clients.admin.NewTopic>
	 */
	public Result<Integer> initTopic(CreateTopicParam param) {
		logger.info("initTopic:{}", param.toString());
		NewTopic newTopic = new NewTopic(param.getTopic(), param.getNumPartitions(), param.getReplicationFactor());
		adminClient.createTopics(Arrays.asList(newTopic));
		return Result.success(1);
	}

	public Result<Map<String, String>> selectTopicInfo(TopicMessageParam param) throws ExecutionException, InterruptedException {
		DescribeTopicsResult result = adminClient.describeTopics(Arrays.asList(param.getTopic()));
		Map<String, TopicDescription> stringTopicDescriptionMap = result.all().get();
//		result.all().get().forEach((k, v) -> System.out.println("k: " + k + " ,v: " + v.toString() + "\n"));

		Map<String, String> resultMap = new HashMap<>(4);
		for (Map.Entry<String, TopicDescription> stringTopicDescriptionEntry : stringTopicDescriptionMap.entrySet()) {
			resultMap.put(stringTopicDescriptionEntry.getKey(), stringTopicDescriptionEntry.getValue().toString());
		}
		return Result.success(resultMap);
	}
}
