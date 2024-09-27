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

public class CustomAdminAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	                                    AuthenticationException exception) throws IOException, ServletException {

	    String matchErrorMessage = "아이디 또는 비밀번호가 일치하지 않습니다.";
	    String noMemberMessage = "일치하는 아이디가 없습니다.";
	    String redirectUrl;

	    // 요청 경로에 따라 리다이렉트 URL 설정
	    String requestURI = request.getRequestURI();
	    if (requestURI.startsWith("/mobile/admin")) {
	        redirectUrl = "/mobile/admin/login?error=true";
	    } else {
	        redirectUrl = "/admin/login?error=true";
	    }

	    // 오류 메시지를 URL 파라미터로 추가
	    if (exception instanceof UsernameNotFoundException) {
	        System.out.println("Handling UsernameNotFoundException");
	        redirectUrl += "&usernameError=" + URLEncoder.encode(noMemberMessage, "UTF-8");
	    } else if (exception instanceof BadCredentialsException) {
	        // BadCredentialsException 처리
	        System.out.println("Handling BadCredentialsException");
	        redirectUrl += "&passwordError=" + URLEncoder.encode(matchErrorMessage, "UTF-8");
	    } else {
	        // 기타 인증 실패 처리
	        System.out.println("Handling generic AuthenticationException");
	        redirectUrl += "&authenticationError=" + URLEncoder.encode("인증 실패", "UTF-8");
	    }

	    response.sendRedirect(redirectUrl);
	}


}



