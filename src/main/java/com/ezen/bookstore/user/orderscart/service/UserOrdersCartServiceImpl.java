package com.ezen.bookstore.user.orderscart.service;

import java.util.List;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import org.springframework.stereotype.Service;

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
			for(UserCartDTO dto : list) {
				List<UserCartDTO> newDto = ordersCartRepository.getProductBasket(dto, member_id);
				
				if(newDto.isEmpty()) {
					ordersCartRepository.productBasketInsert(dto, member_id);
				} else {
					ordersCartRepository.productBasketUpdate(dto, member_id, newDto.get(0).getCart_num());
				}
			}
			result = ordersCartRepository.getBasketCount(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int existBasketItem(String book_isbn, String member_id) {
		return ordersCartRepository.existBasketItems(book_isbn, member_id);
	}

	@Override
	public int updateBasketItemCnt(UserCartDTO dto, String member_id) {
		return ordersCartRepository.updateBasketItemCnt(dto, member_id);
	}
}
