package com.ezen.bookstore.user.mypage.inquiries.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user/mypage")
@Controller
public class UserInquiriesController {
	
	@GetMapping("/inquiries-page")
	public String inquiriesPage() {
		return "user/mypage/inquiries/inquiries";
	}
	
	@GetMapping("/written-inquiries-page")
	public String writtenInquiriesPage() {
		return "user/mypage/inquiries/inquiriesReg";
	}
}
