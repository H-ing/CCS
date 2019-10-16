package com.web.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.goclass.result.LoginResult;
import com.web.model.security.authentication.AuthenticationBean;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "登录api数据样例")
public class LoginController {

	@ApiOperation("实际接口为：/login")
	@PostMapping("/login/example")
	public LoginResult login(@RequestBody AuthenticationBean authenticationBean) {
		return new LoginResult("200", "ok");
	}
	
}
