package com.ezen.bookstore.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.main.dto.UserMainDTO;
import com.ezen.bookstore.user.main.service.UserMainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

	private final UserMainService userMainService;

	@GetMapping("/login")
	public String login() {
		return "/user/login/login";
	}

	@GetMapping("/main")
	public String mainPage(Model model) {
		List<UserMainDTO> banners = userMainService.getBanners();
		List<UserMainDTO> bestBooks = userMainService.getBestBooks();
		List<UserMainDTO> newBooks = userMainService.getNewBooks();
		List<UserMainDTO> topRatedBooks = userMainService.getTopRatedBooks();
		List<UserMainDTO> recommendBooks = userMainService.getRecommendBooks();

		model.addAttribute("banners", banners);
		model.addAttribute("bestBooks", bestBooks);
		model.addAttribute("newBooks", newBooks);
		model.addAttribute("topRatedBooks", topRatedBooks);
		model.addAttribute("recommendBooks", recommendBooks);

		return "user/main/main";
	}

	@GetMapping("/login-status")
	@ResponseBody
	public boolean isLoggedIn() {
		String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);
		return memberId != null;
	}
	
	@GetMapping("/privacy-policy")
	public String privacyPolicyPage() {
		return "user/main/footer_policy/privacy_policy";
	}
	
	@GetMapping("/youth-policy")
	public String youthPolicyPage() {
		return "user/main/footer_policy/youth_policy";
	}
	
	@GetMapping("/terms-policy")
	public String termsePolicyPage() {
		return "user/main/footer_policy/terms_policy";
	}
	
	@GetMapping("/email-policy")
	public String emailPolicyPage() {
		return "user/main/footer_policy/email_policy";
	}
}
