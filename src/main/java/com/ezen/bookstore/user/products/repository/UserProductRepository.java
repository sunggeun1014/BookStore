package com.ezen.bookstore.user.products.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.dto.UserProductDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserProductRepository {

	private final SqlSessionTemplate sql;
	
	public List<UserProductDTO> getProductList(UserSearchCondition condition) {
		return sql.selectList("Products.getProductList", condition);
	}
	
    @GetMapping("/detail")
    public String productDetail() {
        return "/user/products/detail";
    }
}
