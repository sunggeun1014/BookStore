package com.ezen.bookstore.user.members.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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

import com.ezen.bookstore.admin.members.dto.MembersDTO;
import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
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
	
	@GetMapping("/join")
	public String mgntJoin(Model model) {
		
		String[] emailDomainList = { "naver.com", "gmail.com", "daum.net", "nate.com", 
				"hanmail.net", "kakao.com", "outlook.com", "yahoo.co.kr", "icloud.com", "hotmail.com" };
		
		model.addAttribute("emailDomainList", emailDomainList);
			
		return "user/login/members/memberReg";
	}
	
	@PostMapping("/join")
	public String joinProccess(@ModelAttribute UserMembersDTO userMembersDTO, 
					            @RequestParam("emailUser") String emailUser, 
					            @RequestParam("emailDomain") String emailDomain,
					            @RequestParam("countryNum") String countryNum,
					            @RequestParam("userPart1") String userPart1,
					            @RequestParam("userPart2") String userPart2,
					            Model model
								) {

		String member_email = emailUser +"@" + emailDomain;
		String member_phoneNo = countryNum + "-" + userPart1 + "-" + userPart2;
	    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
		
	    userMembersDTO.setMember_email(member_email);
	    userMembersDTO.setMember_phoneNo(member_phoneNo);
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
}
