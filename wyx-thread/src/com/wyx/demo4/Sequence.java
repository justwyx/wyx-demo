package com.wyx.demo4;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
public class Sequence {
	private int value = 0;

	public synchronized int getNext() {
		return value++;
	}

	public static void main(String[] args) {
		Sequence s = new Sequence();
		new Thread(() -> {while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " : " + s.getNext());
		}}).start();

		new Thread(() -> {while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName() + " : " + s.getNext());
		}}).start();

	}
}
