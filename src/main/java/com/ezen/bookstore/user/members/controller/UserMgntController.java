package com.ezen.bookstore.user.members.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.service.UserMgntService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Controller
@RequestMapping("/user/members")
public class UserMgntController {
	
	private final UserMgntService userMgntService;
        
	@GetMapping("/join")
	public String mgntJoin() {			
		return "user/registration/registration";
	}
	
	@GetMapping("/find_Id")
	public String getfindId() {
		return "user/members/findId";
	}
	
	@GetMapping("/find_Pw")
	public String getfindPw() {
		return "user/members/findPw";
	}
	
	@GetMapping("/modifyPwScreen")
	public String showUpdatePw() {
		return "/user/members/findPw-modifyPw";
	}
	
	@GetMapping("/showFindId")
	public String showFindIdPage(@RequestParam String name, @RequestParam String email, RedirectAttributes redirectAttributes, Model model) {

	    List<UserMembersDTO> memberIds = userMgntService.searchMember(name, email);
	    
	    if (memberIds.isEmpty()) {
	        redirectAttributes.addFlashAttribute("errorMessage", "⚠일치하는 회원 정보가 없습니다.");
	        return "redirect:/user/members/find_Id";  
	    }
	    
	    model.addAttribute("name", name);
	    model.addAttribute("memberIds", memberIds);
	    
	    return "user/members/showFindId";  
	}
	
	@GetMapping("/naver/callback")
	public String callbackmgntJoin() {			
		return "user/registration/callback";
	}
}
