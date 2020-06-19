package com.example.springbootes.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/11
 * @Date : 2020/6/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class IndexTest {
	@Autowired
	private IndexService indexService;

	@Test
	public void createIndex() throws IOException {
		boolean test_create_index = indexService.createIndex("test_create_index");
		Assert.assertEquals(true, test_create_index);
	}
}
