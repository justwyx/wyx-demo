package com.wyx.demo3;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
public class Target implements Runnable{

	@Override
	public void run() {
		int i = 0;
		while (true)
		System.out.println(Thread.currentThread().getName() + " : " + i++);
	}
}
