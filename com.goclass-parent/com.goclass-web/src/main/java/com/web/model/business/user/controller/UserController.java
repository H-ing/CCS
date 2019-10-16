package com.web.model.business.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goclass.pojo.TbUser;
import com.web.model.business.user.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/self/{userId}")
	public TbUser getUserInfo(@PathVariable Long userId) {
		return userService.self(userId);
	}
}
