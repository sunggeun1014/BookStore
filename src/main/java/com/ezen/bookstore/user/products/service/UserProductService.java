package com.ezen.bookstore.user.products.service;

import java.util.List;

import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.dto.UserProductDTO;

public interface UserProductService {
	
	public List<UserProductDTO> getProductList(UserSearchCondition condition);
}
