package com.wyx.demo3;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
public class Demo {
	public static void main(String[] args) {
		Thread t1 = new Thread(new Target());
		Thread t2 = new Thread(new Target());

		t1.setName("t1");
		t2.setName("t2");
		// 不要设置
		t1.setPriority(Thread.MIN_PRIORITY);
		t2.setPriority(Thread.MAX_PRIORITY);

		t1.start();
		t2.start();
	}
}
