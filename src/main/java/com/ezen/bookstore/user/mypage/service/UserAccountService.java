package com.ezen.bookstore.user.mypage.service;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import jakarta.servlet.http.HttpSession;

public interface UserAccountService {
	boolean findPwById(HttpSession session, String password);
	boolean updateMemberInfo(HttpSession session, UserMembersDTO userMembersDTO);
	UserMembersDTO getUser(HttpSession session);
	boolean deleteMember(HttpSession session);
}
