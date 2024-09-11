package com.ezen.bookstore.user.bookcategory.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.bookcategory.dto.UserBookCategoryDTO;
import com.ezen.bookstore.user.commons.UserSearchCondition;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserBookCategoryRepository {
	
	private final SqlSessionTemplate sql;
	
	public List<UserBookCategoryDTO> getCategoryList(UserSearchCondition condition) {
		return sql.selectList("BookCategorys.getCategoryList", condition);
	}

}
