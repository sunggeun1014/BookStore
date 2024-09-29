package com.ezen.bookstore.security.service.user;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
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

       // 일반 로그인 성공시
        if (authentication.getPrincipal() instanceof CustomUserDetails) {
            
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            UserMembersDTO membersDTO = userDetails.getMembersDTO();
            membersDTO.setMember_pw(null);
            session.setMaxInactiveInterval(60 * 60);
            session.setAttribute(AccountManagement.MEMBER_INFO, membersDTO);
            
        // 소셜 로그인 성공시    
        } else if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
            
            DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();
            UserMembersDTO membersDTO = (UserMembersDTO) oauthUser.getAttributes().get("userMembersDTO");
            membersDTO.setMember_pw(null);
            session.setAttribute(AccountManagement.MEMBER_INFO, membersDTO); 
        }
        
        response.sendRedirect("/user/main");
    }
}
