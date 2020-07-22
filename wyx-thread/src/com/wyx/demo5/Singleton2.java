package com.wyx.demo5;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/19
 * @Date : 2020/7/19
 */
public class Singleton2 {
	private static volatile Singleton2 singleton;

	private Singleton2() {
	}

	public static Singleton2 getSingleton() {
		if (singleton == null) {
			synchronized(Singleton2.class){
				if (singleton == null) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					singleton = new Singleton2();
				}
			}
		}
		return singleton;
	}
}
