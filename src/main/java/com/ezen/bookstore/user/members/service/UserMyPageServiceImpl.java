package com.ezen.bookstore.user.members.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.mapper.UserMyPageMapper;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Transactional
@RequiredArgsConstructor
@Service
public class UserMyPageServiceImpl implements UserMyPageService {
	
	UserMyPageMapper userMyPageMapper;
	PasswordEncoder passwordEncoder;

	@Override
	@Transactional(readOnly = true)
	public boolean findPwById(HttpSession session, String password) {
		UserMembersDTO membersDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);

		String memberPw = userMyPageMapper.findPwById(membersDTO.getMember_id());
		
		return passwordEncoder.matches(password, memberPw);
	}
	
	@Override
	public boolean updateMemberInfo(HttpSession session, UserMembersDTO userMembersDTO) {
		UserMembersDTO membersDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);
		
		userMembersDTO.setMember_id(membersDTO.getMember_id());
		
		String memberPw = userMembersDTO.getMember_pw();
		
		if (!memberPw.equals("") || !memberPw.isEmpty()) {
			userMembersDTO.setMember_pw( passwordEncoder.encode(memberPw));
		}
			
		
		return userMyPageMapper.updateMemberInfo(userMembersDTO) > 0;
	}
	
	@Override
	public UserMembersDTO getUser(HttpSession session) {
		UserMembersDTO membersDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);
		
		return userMyPageMapper.getUser(membersDTO.getMember_id());
	}
	
	@Override
	public boolean deleteMember(HttpSession session) {
		UserMembersDTO membersDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);
		String memberId = membersDTO.getMember_id();
		
		if (memberId.isEmpty()) {
			return false;
		}
		
		return userMyPageMapper.deleteMember(memberId) > 0;
	}
}
