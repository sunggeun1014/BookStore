package com.ezen.bookstore.user.members.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	public int getBasketCount(String member_id) {
        return sql.selectOne("Members.getBasketCount", member_id);
    }
	
	public List<UserMembersDTO> getFindMember(String name, String email) {
		Map<String, Object> params = new HashMap<>();
		params.put("member_name", name);
		params.put("member_email", email);
		return sql.selectList("Members.findMember", params);
	}
	
	public boolean memberVerification(UserMembersDTO userMembersDTO) {
		int count = sql.selectOne("Members.verification", userMembersDTO);
		return count != 0;
	}
	
	public int modifyPw (UserMembersDTO userMembersDTO) {
		int result = sql.update("Members.updateMemberPw", userMembersDTO);
		
		return result;
	}
	
	
}
