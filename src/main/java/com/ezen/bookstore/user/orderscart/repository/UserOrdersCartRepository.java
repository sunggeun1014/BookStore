package com.ezen.bookstore.user.orderscart.repository;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;

@Repository
@RequiredArgsConstructor
public class UserOrdersCartRepository {
	
	private final SqlSessionTemplate sql;
	
	public int productBasketInsert(UserCartDTO dto, String member_id) {
		dto.setMember_id(member_id);
		
		return sql.insert("OrdersCart.basketInsert", dto);
	}
	
	public int getBasketCount(String member_id) {
		return sql.selectOne("OrdersCart.basketCount", member_id);
	}

	public int existBasketItems(String book_isbn, String member_id) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("member_id", member_id);
		map.put("book_isbn", book_isbn);

		return sql.selectOne("OrdersCart.existItem", map);
	}

	public int updateBasketItemCnt (UserCartDTO dto, String member_id) {
		dto.setMember_id(member_id);
		return sql.update("OrdersCart.updateBasketItem", dto);
	}
}
