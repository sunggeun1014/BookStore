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
	public void productBasketInsert(List<String> book_isbn, String member_id) {
		try {
			for(String isbn : book_isbn) {
				ordersCartRepository.productBasketInsert(isbn, member_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
