package com.wyx.springbootmongodb;

import com.wyx.springbootmongodb.controller.DemoController;
import com.wyx.springbootmongodb.dto.QueryDTO;
import com.wyx.springbootmongodb.entity.Book;
import com.wyx.springbootmongodb.entity.MerchantSku;
import com.wyx.springbootmongodb.service.MongodbService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/7
 * @Date : 2020/7/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongodbServiceTest {
	private static final String MER_100006 = "merchant_sku_100059";

	@Autowired
	private MongodbService mongodbService;


	@Test
	public void query() {
		QueryDTO queryDTO = new QueryDTO();
		queryDTO.setBrandId(Arrays.asList(3));
		queryDTO.setSkip(0L);
		queryDTO.setLimit(10);
		List<MerchantSku> query = mongodbService.query(MER_100006, queryDTO);

		System.out.println("merchantSku:" + query.toString());
	}

	@Test
	public void findById() {
		MerchantSku merchantSku = mongodbService.findById(MER_100006, 1);
		System.out.println("merchantSku:" + merchantSku.toString());
	}

	@Test
	public void findByIdList() {
		List<MerchantSku> merchantSkuList = mongodbService.findByIdList1(MER_100006, Arrays.asList(22434,22433));
		System.out.println("merchantSku:" + merchantSkuList.toString());
	}


	@Test
	public void insertList() {
		List<MerchantSku> merchantSkuList = new ArrayList<>();

		MerchantSku sku1 = new MerchantSku();
		sku1.setId(1);
		sku1.setBrandId(1);
		sku1.setBrandName("施耐德");
		sku1.setItemNumber("13196");
		sku1.setModel("13196");
		sku1.setName("配电箱13196");

//		MerchantSku sku2 = new MerchantSku();
//		sku2.setId(2);
//		sku2.setBrandId(1);
//		sku2.setBrandName("施耐德");
//		sku2.setBrandNameAlias(Arrays.asList("施耐德","SCHNEIDER"));
//		sku2.setItemNumber("04000");
//		sku2.setModel("04000");
//		sku2.setName("插拔式负载平衡系统04000");

		MerchantSku sku3 = new MerchantSku();
		sku3.setId(3);
		sku3.setBrandId(1);
		sku3.setBrandName("施耐德");
		sku3.setItemNumber("04040");
		sku3.setModel("04040");
		sku3.setName("电缆04040");

		merchantSkuList.add(sku1);
		merchantSkuList.add(sku3);

		int num = mongodbService.insertAndIdIsNotNull(MER_100006, merchantSkuList);
		System.out.println("插入成功数量:" + num);
	}


	@Test
	public void upsertById() {
		MerchantSku sku1 = new MerchantSku();
		sku1.setId(1);
		sku1.setBrandId(1);
		sku1.setBrandName("施耐德");
		sku1.setItemNumber("13196");
		sku1.setModel("13196");
		sku1.setName("配电箱13196");

		int num = mongodbService.upsertById(MER_100006, sku1);
		System.out.println("插入成功数量:" + num);
	}

	@Test
	public void batchUpsertById() {
		List<MerchantSku> merchantSkuList = new ArrayList<>();

		MerchantSku sku1 = new MerchantSku();
		sku1.setId(1);
		sku1.setBrandId(1);
		sku1.setBrandName("施耐德");
		sku1.setItemNumber("13196");
		sku1.setModel("13196");
		sku1.setName("配电箱13196");

		MerchantSku sku3 = new MerchantSku();
		sku3.setId(3);
		sku3.setBrandId(1);
		sku3.setBrandName("施耐德");
		sku3.setItemNumber("04040");
		sku3.setModel("04040");
		sku3.setName("电缆04040");

		merchantSkuList.add(sku1);
		merchantSkuList.add(sku3);

		int num = mongodbService.batchUpsertById(MER_100006, merchantSkuList);
		System.out.println("插入成功数量:" + num);
	}
}
