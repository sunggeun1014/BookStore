package com.ezen.bookstore.user.orderscart.repository;

import java.util.HashMap;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserOrdersCartRepository {
	
	private final SqlSessionTemplate sql;
	
	public void productBasketInsert(String book_isbn, String member_id) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("book_isbn", book_isbn);
		map.put("member_id", member_id);
		map.put("qty", 1);
		
		sql.insert("OrdersCart.basketInsert", map);
	}

}
