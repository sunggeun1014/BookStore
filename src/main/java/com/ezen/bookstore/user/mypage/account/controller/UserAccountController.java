package com.ezen.bookstore.user.mypage.account.controller;

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

import com.ezen.bookstore.commons.CommonConstants;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.mypage.account.service.UserAccountService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user/mypage")
@Controller
public class UserAccountController {
    
	UserAccountService myPageService;

	@GetMapping("/update-page")
	public String updatePage() {
		return "/user/mypage/member_update/memberUpdate";
	}
	
	@GetMapping("/delete-page")
	public String deletePage() {
		
		return "/user/mypage/member_del/memberDel";
	}
	
	@PostMapping("/update-check-pw")
	public String checkPw(@RequestParam("member_pw") String password,
				          Model model,
						  HttpServletRequest request) {
		
		if(myPageService.findPwById(password)) {

			UserMembersDTO userMembersDTO =	myPageService.getUser();
			
			String[] emailParts = userMembersDTO.getMember_email().split("@");
			
			String[] phoneNumber = userMembersDTO.getMember_phoneNo().split("-");
			
			model.addAttribute("userMembers", userMembersDTO);
			model.addAttribute("emailDomainList", CommonConstants.EMAIL_DOMAINS);
			model.addAttribute("countryNums", CommonConstants.COUNTRY_NUMS);
			model.addAttribute("emailUser", emailParts[0]);
			model.addAttribute("emailDomain", emailParts[1]);
		    model.addAttribute("countryNum", phoneNumber[0]);
		    model.addAttribute("userPart1", phoneNumber[1]);
		    model.addAttribute("userPart2", phoneNumber[2]);
			
			return "/user/mypage/member_update/memberUpdateDetail";
			
		} else {
			return "redirect:/user/mypage/update-page";
		}
		
	}
	
	@PostMapping("/updatedata")
	public ResponseEntity<Map<String, Object>> updateUser(@RequestBody UserMembersDTO userMembersDTO) {
		Map<String, Object> response = new HashMap<>();
		try {
			myPageService.updateMemberInfo(userMembersDTO);
			
			response.put("success", true);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "회원 정보 수정 중 오류가 발생했습니다.");
			return ResponseEntity.status(500).body(response);
		}
	}
	
	@PostMapping("/delete-check-pw")
	public String deleteDetailPage(@RequestParam("member_pw") String password) {
		
		if(myPageService.findPwById(password)) {
			return "/user/mypage/member_del/memberDelDetail";
		} else {
			return "redirect:/user/mypage/delete-page";
		}
	
	}
	
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteUser(HttpSession session) {
		
		if (myPageService.deleteMember()) {
			session.invalidate();
			return ResponseEntity.ok(Map.of("success", true));
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("success", false, "message", "회원 탈퇴에 실패했습니다."));
		}
	}
}
