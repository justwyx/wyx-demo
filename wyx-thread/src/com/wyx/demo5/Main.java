package com.wyx.demo5;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
public class Main {
	public static void main(String[] args) {
		Singleton singleton = Singleton.getSingleton();
		Singleton singleton1 = Singleton.getSingleton();
		System.out.println(singleton == singleton1);

	}
}
