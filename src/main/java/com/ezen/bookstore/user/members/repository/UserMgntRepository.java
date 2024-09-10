package com.ezen.bookstore.user.members.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserMgntRepository {
	private final SqlSessionTemplate sql;

	public int addMember(UserMembersDTO userMembersDTO) {
		return sql.insert("Members.addMember",userMembersDTO);
	}
	
	public boolean findById(String member_id) {
		return sql.selectList("Members.findById", member_id).size() == 0;
	}
	
}
