package com.ezen.bookstore.admin.security.service;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		
        ManagersDTO managersDTO = userDetails.getManagersDTO();
        managersDTO.setManager_pw(null);
        
        System.out.println(managersDTO);
        
        session.setMaxInactiveInterval(60*60);
        session.setAttribute(AccountManagement.MANAGER_INFO, managersDTO);
        
        response.sendRedirect("/admin/index");

	}

}
