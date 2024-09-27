package com.ezen.bookstore.mobile.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/mobile/admin")
public class MobileController {
	
	@GetMapping("/login")
	public String login() {		
		return "/mobile/login/login";		
	}
	
	@GetMapping("/index")
    public String index(Model model) {
		
        return "mobile/home/home";
       
    }
	
}
