package com.ezen.bookstore.user.orderscart.service;

import java.util.List;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.orderscart.repository.UserOrdersCartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrdersCartServiceImpl implements UserOrdersCartService {

	private final UserOrdersCartRepository ordersCartRepository; 
	
	@Override
	public int productBasketInsert(List<UserCartDTO> list, String member_id) {
		int result = 0;
		
		try {
			result += ordersCartRepository.getBasketCount(member_id);
			for(UserCartDTO dto : list) {
				result += ordersCartRepository.productBasketInsert(dto, member_id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
