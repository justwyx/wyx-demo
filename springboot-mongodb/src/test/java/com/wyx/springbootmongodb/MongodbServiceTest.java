package com.wyx.springbootmongodb;

import com.wyx.springbootmongodb.controller.DemoController;
import com.wyx.springbootmongodb.entity.Book;
import com.wyx.springbootmongodb.service.MongodbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/7
 * @Date : 2020/7/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbServiceTest {

	@Autowired
	private MongodbService mongodbService;

	@Test
	public void save() {
		Book book = new Book();
		book.setId("1");
		book.setPrice(200);
		book.setName("天龙八部3");
//		book.setInfo("金庸");
		book.setCreateTime(new Date());
		mongodbService.save(book);
	}
}
