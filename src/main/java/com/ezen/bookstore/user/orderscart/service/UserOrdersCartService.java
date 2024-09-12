package com.ezen.bookstore.user.orderscart.service;

import java.util.List;

public interface UserOrdersCartService {
	public void productBasketInsert(List<String> book_isbn, String member_idw);
}
