package com.example.springbootes.controller;

import com.example.springbootes.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/12
 * @Date : 2020/6/12
 */
@RequestMapping(value = "/index")
@RestController
public class IndexController {
	@Autowired
	private IndexService indexService;

	@PutMapping("/create/{index}")
	public boolean createIndex(@PathVariable("index") String index) throws IOException {
		return indexService.createIndex(index);
	}
}
