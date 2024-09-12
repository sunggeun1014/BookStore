package com.ezen.bookstore.user.products.repository;

import java.util.List;

import com.ezen.bookstore.user.products.dto.UserReviewDTO;
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

    public UserProductDTO getProductDetail(String bookISBN) {
        return sql.selectOne("Products.getBookDetail", bookISBN);
    }

	public List<UserReviewDTO> getUserReview(String bookISBN) {
		return sql.selectList("Products.getUserReviewList", bookISBN);
	}

	public List<UserReviewDTO> getReviewPercent(String bookISBN) {
		return sql.selectList("Products.getReviewPercent", bookISBN);
	}
}
