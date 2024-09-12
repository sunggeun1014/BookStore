package com.ezen.bookstore.user.products.service;

import java.util.List;

import com.ezen.bookstore.user.products.dto.UserReviewDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.dto.UserProductDTO;
import com.ezen.bookstore.user.products.repository.UserProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
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

	@Override
	public UserProductDTO getProductDetail(String bookISBN) {
		return productRepository.getProductDetail(bookISBN);
	}

	@Override
	public List<UserReviewDTO> getReviewList(String bookISBN) {
		try {
			return productRepository.getUserReview(bookISBN);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<UserReviewDTO> getReviewPercent(String bookISBN) {

		return productRepository.getReviewPercent(bookISBN);
	}
}
