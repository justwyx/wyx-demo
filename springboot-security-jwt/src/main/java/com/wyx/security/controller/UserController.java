package com.wyx.security.controller;

import com.wyx.common.model.DeleteUserParam;
import com.wyx.common.model.LoginParam;
import com.wyx.common.model.Result;
import com.wyx.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

/**
 * @author : Just wyx
 * @Description : TODO 2020/5/23
 * @Date : 2020/5/23
 */
@RestController
@RequestMapping("/api/user")
public class UserController {


	@Autowired
	private UserService userService;

	/**
	 * 获取成功
	 */
	@GetMapping(value = "/login/log", name = "获取登录日志")
	public Result<String> getLoginLog() {
		return Result.success("登录日志");
	}

	/**
	 * username:admin
	 * password:admin
	 */
	@PostMapping(value = "/login", name = "登录，返回token")
	public Result<String> login(@RequestBody LoginParam param) throws AuthenticationException {
		return Result.success(userService.login(param.getUsername(), param.getPassword()));
	}


	/**
	 * 没有权限
	 */
	@DeleteMapping(value = "/delete", name = "删除用户")
	public Result<String> deleteUser(@RequestBody DeleteUserParam param) {
		return Result.success("删除用户成功" + param.getUsername());
	}

}
