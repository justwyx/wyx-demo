package com.wyx.springbootkafka.controller;

import com.wyx.common.model.Result;
import com.wyx.springbootkafka.model.CreateTopicParam;
import com.wyx.springbootkafka.model.TopicMessageParam;
import com.wyx.springbootkafka.service.KafkaProducerService;
//import com.wyx.springbootkafka.service.KafkaService;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.admin.TopicDescription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @Description : TODO 2020/5/25
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
//	@Autowired
//	private KafkaService kafkaService;
	@Autowired
	private KafkaProducerService kafkaProducerService;


//	@PostMapping(value = "/initTopic", name = "创建topic")
//	public Result<Integer> initTopic(@RequestBody CreateTopicParam param) {
//		return kafkaService.initTopic(param);
//	}
//
//	@GetMapping(value = "/selectTopicInfo", name = "查询topic信息")
//	public  Result<Map<String, String>> selectTopicInfo(@RequestBody TopicMessageParam param) throws ExecutionException, InterruptedException {
//		return kafkaService.selectTopicInfo(param);
//	}

	/**
	 * http://localhost:8080/api/kafka/hello
	 * @return
	 */
	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}


	@PostMapping(value = "/sendTest", name = "发送测试消息")
	public Result<Integer> sendTest(@RequestBody TopicMessageParam param) {
		kafkaProducerService.sendMessage(param);
		return Result.success(1);
	}

	@PostMapping(value = "/sendTestList", name = "发送测试消息")
	public Result<Integer> sendTestList(@RequestBody TopicMessageParam param) {
		for (String s : param.getMessageList()) {
			param.setMessage(s);
			kafkaProducerService.sendMessage(param);
		}
		return Result.success(1);
	}

	@PostMapping(value = "/send/ids", name = "发送测试消息")
	public Result<Integer> sendIds(@RequestBody TopicMessageParam param) {
		String topic = param.getTopic();
		for (Integer id : param.getIdList()) {
			kafkaProducerService.sendMessage(topic, id.toString());
		}
		return Result.success(1);
	}
}



