package com.ezen.bookstore.user.products.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.dto.UserProductDTO;
import com.ezen.bookstore.user.products.repository.UserProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
	
	private final UserProductRepository productRepository; 
	
	@Override
	public List<UserProductDTO> getProductList(UserSearchCondition condition) {
		try {
			return productRepository.getProductList(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
