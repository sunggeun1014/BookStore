package com.ezen.bookstore.security.service.admin;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.bookstore.admin.managers.dto.AdminManagersDTO;
import com.ezen.bookstore.commons.AccountManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);
		
		CustomAdminDetails adminDetails = (CustomAdminDetails) authentication.getPrincipal();		
        AdminManagersDTO managersDTO = adminDetails.getManagersDTO();
        managersDTO.setManager_pw(null);
                
        session.setMaxInactiveInterval(60*60);
        session.setAttribute(AccountManagement.MANAGER_INFO, managersDTO);
        
        String requestURI = request.getRequestURI();
        String redirectUrl;
        
        if (requestURI.startsWith("/admin")) {
        	
            redirectUrl = "/admin/home";
        } else if (requestURI.startsWith("/mobile/admin")) {
        	
            redirectUrl = "/mobile/admin/home";
        } else {
            // 기본 리다이렉트 경로 (예상치 못한 경로일 경우)
            redirectUrl = "/admin/home";
        }

        response.sendRedirect(redirectUrl);

	}

}
