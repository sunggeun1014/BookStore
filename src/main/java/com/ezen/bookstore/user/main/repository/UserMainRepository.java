package com.ezen.bookstore.user.main.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.main.dto.UserMainDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Repository
@Slf4j
public class UserMainRepository {

	private final SqlSessionTemplate sql;

	public List<UserMainDTO> getBanners() {
		return sql.selectList("userMain.getBanners");
	}
	
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
