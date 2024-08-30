package com.ezen.bookstore.admin.managers.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.members.dto.MembersDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class MgrRepository {
	private final SqlSessionTemplate sql;
	
	public List<ManagersDTO> getMembers(){
		return sql.selectList("Managers.getAll");
	}
	
	public ManagersDTO getManagerDetails(String managerId){
		return sql.selectOne("Managers.getDetail", managerId);
	}
}
