package com.ezen.bookstore.user.mypage.controller;

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
import com.ezen.bookstore.user.mypage.service.UserAccountServiceImpl;

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
    
	UserAccountServiceImpl myPageService;

	@GetMapping("/update-page")
	public String updatePage(Model model) {
		
		model.addAttribute("page", "update-detail-page");
		
		return "user/mypage/member_update/memberUpdate";
	}
	
	@GetMapping("/delete-page")
	public String deletePage(Model model) {
		
		model.addAttribute("page", "delete-detail-page");
		
		return "user/mypage/member_del/memberDel";
	}
	
	@PostMapping("/check-pw")
	public String checkPw(@RequestParam("page") String page, 
				          @RequestParam("member_pw") String password,
						  HttpSession session,
						  HttpServletRequest request) {
		try {
	
			if(myPageService.findPwById(session, password)) {
				session.setAttribute("isPasswordConfirmed", true);
				
				
				if (page.equals("update-detail-page")) {
					return "redirect:/user/mypage/update-detail-page";
				} else {
					return "redirect:/user/mypage/delete-detail-page";
				}
				
			} else {
				session.removeAttribute("isPasswordConfirmed");
				return "redirect:/user/mypage/" + page;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:" + request.getHeader("Referer");
		}
	}
	
	
	@GetMapping("/update-detail-page")
	public String updateDetailPage(HttpSession session, Model model) {
		Boolean isPasswordConfirmed = (Boolean) session.getAttribute("isPasswordConfirmed");
		
		if (isPasswordConfirmed == null || !isPasswordConfirmed) {
			session.removeAttribute("isPasswordConfirmed");
			return "redirect:/user/mypage/update-page";
		}
		
		
		UserMembersDTO userMembersDTO =	myPageService.getUser(session);

		String[] emailParts = userMembersDTO.getMember_email().split("@");
		String emailUser = emailParts[0];
		String emailDomain = emailParts[1];
		
		String[] phoneNumber = userMembersDTO.getMember_phoneNo().split("-");
		String countryNum = phoneNumber[0];
		String userPart1 = phoneNumber[1];
		String userPart2 = phoneNumber[2];
		
		String[] emailDomainList = CommonConstants.EMAIL_DOMAINS;
		
		String[] countryNums = CommonConstants.COUNTRY_NUMS;
		
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
	
	@GetMapping("/delete-detail-page")
	public String deleteDetailPage(HttpSession session) {
		Boolean isPasswordConfirmed = (Boolean) session.getAttribute("isPasswordConfirmed");
		
		if (isPasswordConfirmed != null && isPasswordConfirmed) {
			session.removeAttribute("isPasswordConfirmed");
			return "user/mypage/member_del/memberDelDetail";
		} else {
			return "redirect:/user/mypage/delete-page";
		}
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
