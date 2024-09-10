package com.ezen.bookstore.user.members.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMyPageMapper {
	String findPwById(String memberId);
}
