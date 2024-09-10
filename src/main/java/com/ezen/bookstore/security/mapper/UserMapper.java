package com.ezen.bookstore.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;
@Mapper
public interface UserMapper {
	@Select("SELECT member_id, member_pw, member_name, member_email, member_phoneNo, member_addr, member_detail_addr, member_date,"
    		+ "naver_login_cd, kakao_login_cd FROM members WHERE member_id = #{username}")
	UserMembersDTO loadUserByUsername(@Param("username") String username);
}
