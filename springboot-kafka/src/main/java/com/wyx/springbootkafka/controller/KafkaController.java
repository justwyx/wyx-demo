package com.wyx.springbootkafka.controller;

import com.wyx.common.model.Result;
import com.wyx.springbootkafka.model.TopicMessageParam;
import com.wyx.springbootkafka.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Description : TODO 2020/5/25
 * @author : Just wyx
 * @Date : 2020/5/25
 */
@RestController
@RequestMapping("/api/kafka")
public class KafkaController {
	@Autowired
	private KafkaProducerService kafkaProducerService;


	@GetMapping(value = "/test", name = "test")
	public Result<Integer> test() {
		return Result.success(1);
	}

	@PostMapping(value = "/sendTest", name = "发送测试消息")
	public Result<Integer> sendTest(@RequestBody TopicMessageParam param) {
		kafkaProducerService.sendTest(param.getMessage());
		return Result.success(1);
	}
}



