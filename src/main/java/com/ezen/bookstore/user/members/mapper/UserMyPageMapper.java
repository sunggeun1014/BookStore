package com.ezen.bookstore.user.members.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

@Mapper
public interface UserMyPageMapper {
	String findPwById(String memberId);
	int updateMemberInfo(UserMembersDTO userMembersDTO);
	UserMembersDTO getUser(String memberId);
}
