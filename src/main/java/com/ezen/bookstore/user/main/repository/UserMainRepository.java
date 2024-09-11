package com.ezen.bookstore.user.main.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.main.dto.UserMainDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserMainRepository {

	private final SqlSessionTemplate sql;

	public List<UserMainDTO> getBestBooks() {
		return sql.selectList("userMain.getBestBooks");
	}
	
    public List<UserMainDTO> getNewBooks() {
        return sql.selectList("userMain.getNewBooks");
    }
    
    public List<UserMainDTO> getTopRatedBooks() {
    	return sql.selectList("userMain.getTopRatedBooks");
    }
    
    public List<UserMainDTO> getRecommendBooks() {
    	return sql.selectList("userMain.getRecommendBooks");
    }
	
}
