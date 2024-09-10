package com.ezen.bookstore.user.main.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.products.dto.ProductsDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserMainRepository {

	private final SqlSessionTemplate sql;
	

    public List<ProductsDTO> getNewBooks() {
        return sql.selectList("Products.getNewBooks");
    }
	
}
