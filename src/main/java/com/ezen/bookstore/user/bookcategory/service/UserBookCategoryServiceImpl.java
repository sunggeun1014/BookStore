package com.ezen.bookstore.user.bookcategory.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.bookcategory.dto.UserBookCategoryDTO;
import com.ezen.bookstore.user.bookcategory.repository.UserBookCategoryRepository;
import com.ezen.bookstore.user.commons.UserSearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookCategoryServiceImpl implements UserBookCategoryService {
	
	private final UserBookCategoryRepository bookCategoryRepository;    
	
	@Override
	public List<UserBookCategoryDTO> getCategoryList(UserSearchCondition condition) {
		try {
			return bookCategoryRepository.getCategoryList(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
