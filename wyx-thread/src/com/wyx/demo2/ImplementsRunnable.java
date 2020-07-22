package com.wyx.demo2;


public class ImplementsRunnable implements Runnable{
	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName());
	}


	public static void main(String[] args) {
		Thread t = new Thread(new ImplementsRunnable());
		t.start();
	}
}
