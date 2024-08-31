package com.ezen.bookstore.admin.managers.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;

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
	
	public void changeAllByDept(String managerId, String dept) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("managerId", managerId);
	    params.put("dept", dept);
		sql.update("Managers.updateManagerDept", params);
	}
	
	public int addManager(ManagersDTO managersDTO) {
			
		return sql.insert("Managers.addManager",managersDTO);
	}
}
