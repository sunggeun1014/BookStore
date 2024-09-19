package com.ezen.bookstore.user.mypage.account.service;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import jakarta.servlet.http.HttpSession;

public interface UserAccountService {
	boolean findPwById(String password);
	boolean updateMemberInfo(UserMembersDTO userMembersDTO);
	UserMembersDTO getUser();
	boolean deleteMember();
}
