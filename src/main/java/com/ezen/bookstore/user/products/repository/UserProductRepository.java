package com.ezen.bookstore.user.products.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.products.dto.UserProductDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserProductRepository {

	private final SqlSessionTemplate sql;
	
	public List<UserProductDTO> getProductList() {
		return null;
	}
}
