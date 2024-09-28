package com.ezen.bookstore.mobile.dispatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/mobile/admin")
public class DispatchController {
	
	@GetMapping("/dispatch-page")
	public String getDispatchPage() {
		return "mobile/dispatch/dispatch";
	}
	
	
}
