package com.ezen.bookstore.user.orderscart.service;

import java.util.List;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;

public interface UserOrdersCartService {
	public int productBasketInsert(List<UserCartDTO> list, String member_idw);

	public int existBasketItem(String book_isbn, String member_idw);

	public int updateBasketItemCnt(UserCartDTO dto, String member_id);
}
