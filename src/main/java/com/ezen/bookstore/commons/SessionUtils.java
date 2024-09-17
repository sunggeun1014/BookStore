package com.ezen.bookstore.commons;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.ezen.bookstore.admin.commons.AdminSessionInfo;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionUtils {
	
	// 현재 세션을 불러오는 메서드
	private static HttpSession getSession() {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
		HttpServletRequest httpRequest = servletRequestAttributes.getRequest();
		HttpSession session = httpRequest.getSession(false);
		return session;
	}
	
    // 현재 로그인한 사용자 정보를 가져오는 메서드 (Spring Security 사용)
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

	
	public static String getUserAttribute(UserSessionInfo info) {
		HttpSession session = getSession();
		
		if(session == null) {
			return null;
		}
		
		UserMembersDTO dto = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);
		
		if(dto == null) {
			return null;
		}
		
		switch(info) {
		case MEMBER_ID :
			return dto.getMember_id();
		case MEMBER_NAME :
			return dto.getMember_email();
		case MEMBER_DATE :
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");
			Date date = new Date();
			date.setTime(dto.getMember_date().getTime()); 
			return formatter.format(date);
		case MEMBER_EAMIL :
			return dto.getMember_email();
		default : 
			return null;
		}
	}
	
	
	public static String getAdminAttribute(AdminSessionInfo info) {
		HttpSession session = getSession();
		
		if(session == null) {
			return null;
		}
		
		ManagersDTO dto = (ManagersDTO) session.getAttribute(AccountManagement.MANAGER_INFO);
		
		if(dto == null) {
			return null;
		}
		
		switch(info) {
		case MANAGER_ID :
			return dto.getManager_id();
		case MANAGER_NAME :
			return dto.getManager_name();
		case MANAGER_ADDR :
			return dto.getManager_addr();
		case MANAGER_DETAIL_ADDR :
			return dto.getManager_detail_addr();
		case MANAGER_EAMIL :
			return dto.getManager_email();
		case MANAGER_PHONE_NO :
			return dto.getManager_phoneNo();
		default : 
			return null;
		}
	}
}
