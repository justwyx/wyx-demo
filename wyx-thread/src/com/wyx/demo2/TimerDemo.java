package com.wyx.demo2;

import java.util.Timer;
import java.util.TimerTask;


public class TimerDemo {
	public static void main(String[] args) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName());
			}
		}, 0, 1000);
	}
}
