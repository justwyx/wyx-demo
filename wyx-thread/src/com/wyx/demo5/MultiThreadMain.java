package com.wyx.demo5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
public class MultiThreadMain {
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newFixedThreadPool(20);

		for (int i = 0; i < 20; i++) {
			executorService.execute(() -> System.out.println(Thread.currentThread().getName() + Singleton2.getSingleton()));
		}
		executorService.shutdown();
	}
}
