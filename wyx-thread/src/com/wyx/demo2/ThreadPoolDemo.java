package com.wyx.demo2;


import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {
	public static void main(String[] args) {
		Executor threadPool = Executors.newFixedThreadPool(10);

		while (true) {
//			try {
//				Thread.sleep(3 * 1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			threadPool.execute(() -> System.out.println(Thread.currentThread().getName()));
		}

	}
}
