//package com.wyx.springbootkafka;
//
//import com.alibaba.fastjson.JSONObject;
//import com.wyx.common.model.Result;
//import com.wyx.springbootkafka.model.CreateTopicParam;
//import com.wyx.springbootkafka.model.TopicMessageParam;
//import com.wyx.springbootkafka.service.KafkaProducerService;
//import com.wyx.springbootkafka.service.KafkaService;
//import org.apache.kafka.clients.admin.NewTopic;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
///**
// * @author : Just wyx
// * @Description : TODO 2020/5/25
// * @Date : 2020/5/25
// */
//@SpringBootTest
//@RunWith(SpringRunner.class)
//public class KafkaTest {
//
//	@Autowired
//	private KafkaService kafkaService;
//	@Autowired
//	private KafkaProducerService kafkaProducerService;
//
//	@Test
//	public void testCreateTopic() {
//		CreateTopicParam createTopicParam = new CreateTopicParam();
//		createTopicParam.setTopic("topic.init.demo");
//		createTopicParam.setNumPartitions(3);
//		createTopicParam.setReplicationFactor((short) 2);
//		Result<Integer> newTopicResult = kafkaService.initTopic(createTopicParam);
//		System.out.println(JSONObject.toJSONString(newTopicResult));
//	}
//
//
//	@Test
//	public void testSendMessage() {
//		TopicMessageParam topicMessageParam = new TopicMessageParam();
//		topicMessageParam.setTopic("topic.init.demo");
//		topicMessageParam.setMessage("一个测试消息");
//		kafkaProducerService.sendMessage(topicMessageParam);
//		//休眠5秒，为了使监听器有足够的时间监听到topic的数据
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//}
