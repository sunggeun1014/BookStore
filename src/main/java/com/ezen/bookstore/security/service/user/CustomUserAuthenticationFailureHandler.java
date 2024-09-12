package com.ezen.bookstore.security.service.user;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomUserAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
	        AuthenticationException exception) throws IOException, ServletException {

	    String redirectUrl = "/user/login?error=true";

	    if (exception instanceof UsernameNotFoundException) {
	        redirectUrl += "&usernameError=" + URLEncoder.encode("⚠아이디를 찾을 수 없습니다", "UTF-8");
	    } else if (exception instanceof BadCredentialsException) {
	        redirectUrl += "&passwordError=" + URLEncoder.encode("⚠아이디와 비밀번호를 확인해주세요.", "UTF-8");
	    }

	    response.sendRedirect(redirectUrl);
	}

}
