package com.wyx.demo2;


public class AnonymousClass {
	public static void main(String[] args) {
		new Thread(() -> System.out.println(Thread.currentThread().getName())).start();
	}
}
