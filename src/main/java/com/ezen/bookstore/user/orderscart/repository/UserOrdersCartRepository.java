package com.ezen.bookstore.user.orderscart.repository;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserOrdersCartRepository {
	
	private final SqlSessionTemplate sql;
	
	public int productBasketInsert(UserCartDTO dto, String member_id) {
		dto.setMember_id(member_id);
		
		return sql.insert("OrdersCart.basketInsert", dto);
	}
	
	public List<UserCartDTO> getProductBasket(UserCartDTO dto, String member_id) {
		dto.setMember_id(member_id);
		
		return sql.selectList("OrdersCart.getProductBasket", dto);
	}
	
	public void productBasketUpdate(UserCartDTO dto, String member_id, Integer cart_num) {
		dto.setMember_id(member_id);
		dto.setCart_num(cart_num);
		
		sql.update("OrdersCart.basketUpdate", dto);;
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
