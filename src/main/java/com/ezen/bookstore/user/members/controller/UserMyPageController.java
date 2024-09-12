package com.ezen.bookstore.user.members.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.service.UserMyPageServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
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

	@GetMapping("/updatePage")
	public String updatePage(Model model) {
		
		model.addAttribute("page", "updateDetailPage");
		
		return "user/mypage/member_update/memberUpdate";
	}
	
	@GetMapping("/deletePage")
	public String deletePage(Model model) {
		
		model.addAttribute("page", "deleteDetailPage");
		
		return "user/mypage/member_del/memberDel";
	}
	
	@PostMapping("/checkPw")
	public String checkPw(@RequestParam("page") String page, 
				          @RequestParam("member_pw") String password,
						  HttpSession session,
						  HttpServletRequest request) {
		try {
	
			if(myPageService.findPwById(session, password)) {
			
				if (page.equals("updateDetailPage")) {
					return "redirect:/user/mypage/updateDetailPage";
				} else {
					return "redirect:/user/mypage/deleteDetailPage";
				}
				
			} else {
				return "redirect:/user/mypage/" + page;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + request.getHeader("Referer");
		}
	}
	
	
	@GetMapping("/updateDetailPage")
	public String updateDetailPage(HttpSession session, Model model) {
		UserMembersDTO userMembersDTO =	myPageService.getUser(session);

		String[] emailParts = userMembersDTO.getMember_email().split("@");
		String emailUser = emailParts[0];
		String emailDomain = emailParts[1];
		
		String[] phoneNumber = userMembersDTO.getMember_phoneNo().split("-");
		String countryNum = phoneNumber[0];
		String userPart1 = phoneNumber[1];
		String userPart2 = phoneNumber[2];
		
		String[] emailDomainList = { "naver.com", "gmail.com", "daum.net", "nate.com", 
				"hanmail.net", "kakao.com", "outlook.com", "yahoo.co.kr", "icloud.com", "hotmail.com" };
		
		String[] countryNums = {"010", "011", "016", "017", "018", "019"};
		
		model.addAttribute("userMembers", userMembersDTO);
		model.addAttribute("emailDomainList", emailDomainList);
		model.addAttribute("countryNums", countryNums);
		model.addAttribute("emailUser", emailUser);
		model.addAttribute("emailDomain", emailDomain);
	    model.addAttribute("countryNum", countryNum);
	    model.addAttribute("userPart1", userPart1);
	    model.addAttribute("userPart2", userPart2);
		
		return "user/mypage/member_update/memberUpdateDetail";
	}
	
	@GetMapping("/deleteDetailPage")
	public String deleteDetailPage() {
		
		return "user/mypage/member_del/memberDelDetail";
	}
	
	
	@PostMapping("/updatedata")
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserMembersDTO userMembersDTO,
														  HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
        	myPageService.updateMemberInfo(session, userMembersDTO);
        	
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "회원 정보 수정 중 오류가 발생했습니다.");
            return ResponseEntity.status(500).body(response);
        }
    }
	
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteUser(HttpSession session) {
		
		if (myPageService.deleteMember(session)) {
			session.invalidate();
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			  return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "회원 탈퇴에 실패했습니다."));
		}
	}
}
