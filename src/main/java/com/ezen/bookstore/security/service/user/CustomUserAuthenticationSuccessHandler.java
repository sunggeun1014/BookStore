package com.ezen.bookstore.security.service.user;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomUserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		HttpSession session = request.getSession(true);
		
		CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
		
		UserMembersDTO membersDTO = userDetails.getMembersDTO();
		membersDTO.setMember_pw(null);
		
		session.setMaxInactiveInterval(60*60);
        session.setAttribute(AccountManagement.MEMBER_INFO, membersDTO);
        
        response.sendRedirect("/user/main");

	}
	

}
