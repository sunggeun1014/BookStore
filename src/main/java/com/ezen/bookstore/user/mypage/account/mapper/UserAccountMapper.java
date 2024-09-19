package com.ezen.bookstore.user.mypage.account.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

@Mapper
public interface UserAccountMapper {
	String findPwById(String memberId);
	int updateMemberInfo(UserMembersDTO userMembersDTO);
	UserMembersDTO getUser(String memberId);
	int deleteMember(String memberId);
}
