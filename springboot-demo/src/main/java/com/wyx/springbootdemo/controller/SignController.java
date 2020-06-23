package com.wyx.springbootdemo.controller;

import com.wyx.common.model.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.wyx.springbootdemo.dto.ExternalParam;

import javax.validation.Valid;

/**
 * @author : Just wyx
 * @Description : TODO 2020/6/23
 * @Date : 2020/6/23
 */
@Validated
@RestController
@RequestMapping("/sign")
public class SignController {

	@PostMapping(value = "/check")
	public Result<String> check(@RequestBody @Valid ExternalParam param) {
		//

		return Result.success(param.toString());
	}
}
