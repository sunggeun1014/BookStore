package com.ezen.bookstore.user.mypage.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.mypage.mapper.UserAccountMapper;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequiredArgsConstructor
@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	UserAccountMapper userMyPageMapper;
	PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional(readOnly = true)
	public boolean findPwById( String password) {
		String memberPw = userMyPageMapper.findPwById(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		return passwordEncoder.matches(password, memberPw);
	}
	
	@Override
	public boolean updateMemberInfo(UserMembersDTO userMembersDTO) {
		userMembersDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		String memberPw = userMembersDTO.getMember_pw();
		
		if (StringUtils.hasText(memberPw)) {
			userMembersDTO.setMember_pw( passwordEncoder.encode(memberPw));
		}
		
		return userMyPageMapper.updateMemberInfo(userMembersDTO) > 0;
	}
	
	@Override
	public UserMembersDTO getUser() {
		return userMyPageMapper.getUser(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
	}
	
	@Override
	public boolean deleteMember() {
		String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);
		
		if (memberId == null) {
			return false;
		}
		
		return userMyPageMapper.deleteMember(memberId) > 0;
	}
}
