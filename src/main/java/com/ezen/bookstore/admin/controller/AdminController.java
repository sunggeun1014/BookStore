package com.ezen.bookstore.admin.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.security.service.CustomUserDetails;

@RequestMapping("/admin")
@Controller
public class AdminController {
	
	@GetMapping("/login")
    public String login(Authentication authentication) {

        return "/admin/login/login";
    }
	
	@GetMapping("/index")
    public String index(Model model, String path) {
        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String username = userDetails.getManagerName();
            
            // 부서명을 설정
            String departmentName;
            switch (userDetails.getManagerDept()) {
                case "01":
                    departmentName = "물류팀";
                    break;
                case "02":
                    departmentName = "운영팀";
                    break;
                default:
                    departmentName = "기타";
                    break;
            }

            model.addAttribute("username", username);
            model.addAttribute("authority", departmentName);
        }

        model.addAttribute("template", path);
        
        return "/admin/index";
    }
}
