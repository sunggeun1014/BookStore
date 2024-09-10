package com.ezen.bookstore.user.members.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.service.UserMyPageServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user/mypage")
@Controller
public class UserMyPageController {
    
	UserMyPageServiceImpl myPageService;

	@GetMapping("/update")
	public String updatePage() {
		return "user/mypage/member_update/memberUpdate";
	}
	
	@PostMapping("/checkPw")
	public String checkPw(@RequestParam("member_pw") String password,
						  HttpSession session) {

		if(myPageService.findPwById(session, password)) {
			return "redirect:/user/mypage/updateDetail";
		}
		return "redirect:/user/mypage/update";
	}
	
	
	@GetMapping("/updateDetail")
	public String updateDetailPage(HttpSession session, Model model) {
		UserMembersDTO dto = (UserMembersDTO)session.getAttribute(AccountManagement.MEMBER_INFO);
		
		model.addAttribute("member", dto.getMember_id());
		
		return "user/mypage/member_update/memberUpdateDetail";
	}
	
	
}
