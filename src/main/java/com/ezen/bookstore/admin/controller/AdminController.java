package com.ezen.bookstore.admin.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.ezen.bookstore.admin.home.service.HomeService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.managers.service.MgrService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
	
	private final MgrService mgrService;
	private final HomeService homeService;
	
	@GetMapping("/login")
    public String login(Authentication authentication) {

        return "/admin/login/login";
    }
	
	@GetMapping("/index")
    public String index(Model model) {
		int productCnt = homeService.getProductsCnt();
		int memberCnt = homeService.getMembersCnt();
		int orderCnt = homeService.getTodayOrder();

		int allOrderCnt = homeService.getAllOrders();
		int allDelivering = homeService.getAllDelivering();
		int allCompleted = homeService.getAllCompleted();

		model.addAttribute("productCnt", productCnt);
		model.addAttribute("memberCnt", memberCnt);
		model.addAttribute("orderCnt", orderCnt);
		model.addAttribute("allOrderCnt", allOrderCnt);
		model.addAttribute("allDelivering", allDelivering);
		model.addAttribute("allCompleted", allCompleted);

        return "admin/home/home";
       
    }
	
	@GetMapping("/myinfo")
	public String getMyInfo(HttpSession session, Model model) {
	    
	    String[] emailDomainList = { "naver.com", "gmail.com", "daum.net", "nate.com", 
	    		"hanmail.net", "kakao.com", "outlook.com", "yahoo.co.kr", "icloud.com", "hotmail.com" };

	    ManagersDTO sessionDTO = (ManagersDTO) session.getAttribute(AccountManagement.MANAGER_INFO);
	    ManagersDTO managerDetails = mgrService.detailList(sessionDTO.getManager_id()); 

	    String[] emailParts = managerDetails.getManager_email().split("@");
	    String emailUser = emailParts[0];
	    String emailDomain = emailParts[1];
		
		
	    
	    String fullPhone = managerDetails.getManager_phoneNo();
	    String countryNum = "";
	    String userPart1 = "";
	    String userPart2 = "";

	    if (fullPhone != null && !fullPhone.isEmpty()) {
	        String[] phoneParts = fullPhone.split("-");
	        if (phoneParts.length == 3) {
	            countryNum = phoneParts[0];
	            userPart1 = phoneParts[1];
	            userPart2 = phoneParts[2];
	        }
	    }
	    
	    model.addAttribute("emailDomainList", emailDomainList);	    
	    model.addAttribute("managers", managerDetails);
	    model.addAttribute("emailUser", emailUser);
	    model.addAttribute("emailDomain", emailDomain);
	    model.addAttribute("countryNum", countryNum);
	    model.addAttribute("userPart1", userPart1);
	    model.addAttribute("userPart2", userPart2);
	    
	    return "admin/myinfo/myinfo";
	}

	
	@PostMapping("/myinfo/update")
    public String updateMyInfo(@ModelAttribute("managers") ManagersDTO managersDTO,
                               @RequestParam MultipartFile profileImage) {
		try {
			mgrService.updateManager(managersDTO, profileImage);
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/admin/myinfo";
		}
		
        return "redirect:/admin/index";  // 업데이트 후 마이페이지로 리다이렉트
    }
    
}
