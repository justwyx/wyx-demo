package com.wyx.demo2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ImplementsCallable implements Callable<Integer> {
	@Override
	public Integer call() throws Exception {
		System.out.println("call:" + Thread.currentThread().getName());
		return 0;
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		FutureTask<Integer> task = new FutureTask(new ImplementsCallable());
		Thread t = new Thread(task);
		t.start();
		System.out.println("主任务处理");

		Integer result = task.get();
		System.out.println("result: " + result);
	}
}
























