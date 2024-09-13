package com.ezen.bookstore.user.orderscart.repository;

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
	
	public int getBasketCount(String member_id) {
		return sql.selectOne("OrdersCart.basketCount", member_id);
	}

}
