package com.wyx.demo1;


public class NewThread implements Runnable{

	@Override
	public void run() {
		System.out.println("start: " + Thread.currentThread().getName());
		try {
			Thread.sleep(3 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("hello thread: " + Thread.currentThread().getName());
	}


	public static void main(String[] args) {
		Thread thread = new Thread(new NewThread());
		thread.start();
		System.out.println("main: " + Thread.currentThread().getName());


	}
}
