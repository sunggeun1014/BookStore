package com.ezen.bookstore.user.members.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.EmailDTO;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.service.EmailService;
import com.ezen.bookstore.user.members.service.UserMgntService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/user/members")
public class UserMgntController {
	
	private final UserMgntService userMgntService;
    private final EmailService emailService;

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
	
	
	@PostMapping("/join")
	public String joinProccess(@ModelAttribute UserMembersDTO userMembersDTO) {

	    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		
	    userMembersDTO.setMember_date(now);
		
	    userMgntService.joinProcess(userMembersDTO);
		
		return "redirect:/user/login";
	}
	
	@PostMapping("/checkId")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> checkManagerId(@RequestBody Map<String, String> payload) {
	    String memberId = payload.get("member_id");
	    boolean isAvailable = userMgntService.isMemberIdAvailable(memberId);
	    
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("isAvailable", isAvailable);
	    
	    return ResponseEntity.ok(response);
	}
	
	@GetMapping(value = "/getBasketCount")
    @ResponseBody
    public int getBasketCount(HttpSession session) {
		UserMembersDTO member = (UserMembersDTO)session.getAttribute(AccountManagement.MEMBER_INFO);
        
        if(member != null) {
            return userMgntService.getBasketCount(member.getMember_id());
        }
        
        return 0;
    }
	
	
	@PostMapping("/verify")
	public ResponseEntity verify(@RequestBody EmailDTO emailDTO) {
	    emailDTO.setSubject("[BOOKSTORE]이메일 인증을 위한 인증 코드 발송");

		String code = emailService.sendMail(emailDTO);		
		
		emailDTO.setVerifyCode(code);
		
		return ResponseEntity.ok(emailDTO);
	}
	
	@PostMapping("/verify/check")
	public ResponseEntity<String> verifyCode(@RequestBody EmailDTO emailDTO) {
	    // 인증번호 확인
	    boolean isVerified = emailService.verifyCode(emailDTO.getTo(), emailDTO.getVerifyCode());  

	    if (isVerified) {
	        // 인증 성공 시 응답
	        return ResponseEntity.ok("{\"message\":\"인증이 완료되었습니다.\"}");
	    } else {
	        // 인증 실패 시 응답
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"인증이 실패하였습니다.\"}");
	    }
	}
	
	@PostMapping("/find_Id")
	public String postFindId(@RequestParam String name, @RequestParam String email, RedirectAttributes redirectAttributes) {
	    
		
		redirectAttributes.addAttribute("name", name);
		redirectAttributes.addAttribute("email", email);
	    
	    return "redirect:/user/members/showFindId";
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
	
	@PostMapping("/verification")
	@ResponseBody
	public Map<String, String> ckeckInfo(UserMembersDTO usermembersDTO, HttpSession session) {
	    Map<String, String> response = new HashMap<>();
	    
		if(userMgntService.isMemberVerification(usermembersDTO)) {
			session.setAttribute("member_id", usermembersDTO.getMember_id());
			
			response.put("status", "true");

		}else {
			
			response.put("status", "false");
		}
		return response;
	}
	
	@PostMapping("/modifyPw")
	@ResponseBody
	public Map<String, String> updatePw(@RequestBody UserMembersDTO usermembersDTO) {
		Map<String, String> response = new HashMap<>();
		
		if(userMgntService.modifyMemberPw(usermembersDTO)) {
			response.put("status", "success");
		} else {
			response.put("status", "fail");
		}
		
		return response;
		
	}
	
	@GetMapping("/modifyPwScreen")
	public String showUpdatePw() {
		return "/user/members/findPw-modifyPw";
	}
	
}
