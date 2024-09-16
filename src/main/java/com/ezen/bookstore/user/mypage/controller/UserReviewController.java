package com.ezen.bookstore.user.mypage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;
import com.ezen.bookstore.user.mypage.service.UserReviewService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user/mypage")
@Controller
public class UserReviewController {
	
	UserReviewService reviewService;
	
	@GetMapping("/my-reviews-page")
	public String myReviewPage() {
		
		return "user/mypage/review/myReview";
	}
	// @RequestParam("orderDetailNum") Long orderDetailNum, 
	@GetMapping("/write-review")
	public String writeReviewPageModel(Model model) {
		UserBookReviewDTO userBookReviewDTO = reviewService.getOrderDetail(4999989);
		
		model.addAttribute("userBookReviewDTO", userBookReviewDTO);
		
		return "user/mypage/review/writeReview";
	}
	
}
