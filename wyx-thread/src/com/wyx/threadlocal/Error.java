package com.wyx.threadlocal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Just wyx
 * @Description : TODO 2020/7/29
 * @Date : 2020/7/29
 */

public class Error
{
	private List<String> messages = new ArrayList<String>();

	public Error()
	{

	}

	public Error message(String message)
	{
		this.messages.add(message);
		return this;
	}

	public Error reset()
	{
		messages.clear();
		return this;
	}

	@Override
	public String toString()
	{
		StringBuilder description = new StringBuilder();

		for (String msg : messages)
		{
			description.append("### ");
			description.append(msg);
			description.append("\n");
		}

		return description.toString();
	}

	public static void main(String[] args)
	{
		//新建一个Error类，将它分别用在三个线程里面：main，task1，task2
		// 这种编码用法非常平淡，没有特色，大家都这么用，最后Error被三个线程乱七八糟的塞进了各种东西
		final Error error = new Error();

		error.message("Main Thread Message");
		System.out.println(error);

		Runnable task1 = () -> {
			error.message("Task1 Thread Message");
			System.out.println(error);

		};

		Runnable task2 = () -> {
			error.message("Task2 Thread Message");
			System.out.println(error);

		};

		new Thread(task1).start();

		new Thread(task2).start();

	}
}