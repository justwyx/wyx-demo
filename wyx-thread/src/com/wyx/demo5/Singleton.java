package com.wyx.demo5;


public class Singleton {
	private static Singleton singleton = new Singleton();

	private Singleton() {
	}

	public static Singleton getSingleton() {
		return singleton;
	}


}
