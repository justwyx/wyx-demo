package com.wyx.springboot.lock.zk.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

	/**
	 * http://localhost:prot/demo/hello
	 * @return
	 */
	@GetMapping("/hello")
	public String hello() {
		return "hello world";
	}
}