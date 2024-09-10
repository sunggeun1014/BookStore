package com.ezen.bookstore.security.service.admin;


import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CustomAdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String errorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
        String redirectUrl = "/admin/login?error=true";
        


        if (exception instanceof UsernameNotFoundException) {
            if ("접근 권한이 없습니다.".equals(exception.getMessage())) {
                redirectUrl += "&accessError=" + URLEncoder.encode(exception.getMessage(), "UTF-8");
            } else {
                redirectUrl += "&usernameError=" + URLEncoder.encode(exception.getMessage(), "UTF-8");
            }
        } else if (exception instanceof BadCredentialsException) {
            redirectUrl += "&passwordError=" + URLEncoder.encode(errorMessage, "UTF-8");
        }

        response.sendRedirect(redirectUrl);
    }
    
    
}


