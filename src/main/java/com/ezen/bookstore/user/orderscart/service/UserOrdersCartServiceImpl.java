package com.ezen.bookstore.user.orderscart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.orderscart.repository.UserOrdersCartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrdersCartServiceImpl implements UserOrdersCartService {

	private final UserOrdersCartRepository ordersCartRepository; 
	
	@Override
	public int productBasketInsert(List<String> book_isbn, String member_id) {
		int result = 0;
		
		try {
			result += ordersCartRepository.getBasketCount(member_id);
			for(String isbn : book_isbn) {
				result += ordersCartRepository.productBasketInsert(isbn, member_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
}
