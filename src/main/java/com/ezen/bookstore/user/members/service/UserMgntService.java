package com.ezen.bookstore.user.members.service;

import java.util.List;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

public interface UserMgntService {
	void joinProcess(UserMembersDTO userMembersDTO);
	boolean isMemberIdAvailable(String memberId);
	boolean isKakaoIdAvailable(String kakaoId);
	boolean isNaverIdAvailable(String naverId);
	List<UserMembersDTO> searchMember(String name, String email);
	boolean isMemberVerification(UserMembersDTO userMembersDTO);
	boolean modifyMemberPw(UserMembersDTO userMembersDTO);
	int getBasketCount(String member_id);
	
	
}
