package com.ezen.bookstore.user.products.service;

import java.util.List;

import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.dto.UserProductDTO;
import com.ezen.bookstore.user.products.dto.UserReviewDTO;

public interface UserProductService {
	
	public List<UserProductDTO> getProductList(UserSearchCondition condition);

	public UserProductDTO getProductDetail(String bookISBN);

	public List<UserReviewDTO> getReviewList(String bookISBN);

	public List<UserReviewDTO> getReviewPercent(String bookISBN);
}
