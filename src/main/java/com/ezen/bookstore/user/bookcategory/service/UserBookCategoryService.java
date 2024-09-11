package com.ezen.bookstore.user.bookcategory.service;

import java.util.List;

import com.ezen.bookstore.user.bookcategory.dto.UserBookCategoryDTO;
import com.ezen.bookstore.user.commons.UserSearchCondition;

public interface UserBookCategoryService {
	public List<UserBookCategoryDTO> getCategoryList(UserSearchCondition condition);
}
