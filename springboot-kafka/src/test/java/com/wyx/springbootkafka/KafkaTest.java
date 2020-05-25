package com.wyx.springbootkafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/25
 * @Date : 2020/5/25
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaTest {

	@Autowired
	private KafkaTemplate kafkaTemplate;

	@Test
	public void testDemo() throws InterruptedException {
		kafkaTemplate.send("topic.quick.demo", "this is my first demo");
		//休眠5秒，为了使监听器有足够的时间监听到topic的数据
		Thread.sleep(5000);
	}
}
