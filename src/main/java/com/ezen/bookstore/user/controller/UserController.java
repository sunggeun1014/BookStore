package com.ezen.bookstore.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
	
	@GetMapping("/login")
	public String login() {
		return "/user/login/login";
	}
	
	@GetMapping("/main")
	public String mainPage() {
		return "/user/main/main";
	}
}
