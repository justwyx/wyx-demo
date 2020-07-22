package com.wyx.demo2;


import java.util.Arrays;
import java.util.List;

public class LamdaDemo {
	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(10,20,30,40);

		System.out.println(list.parallelStream().mapToInt(i -> i).sum());
	}
}
