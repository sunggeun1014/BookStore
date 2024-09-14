package com.ezen.bookstore.user.cart.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class UserCartRepository {

	private final SqlSessionTemplate sql;
	
	public List<UserCartDTO> getCartItemList(String memberId) {
		return sql.selectList("Cart.getCartItemList", memberId);
	};
	
	public void addCartItem (UserCartDTO userCartDTO) {
		sql.insert("Cart.addCartItem", userCartDTO);
	}
	
    public void deleteItemsByCartNums(List<Integer> cartNums) {
        sql.delete("Cart.deleteItemsByCartNums", cartNums);
    }
	
	public void deleteItemsByMemberId (String memberId) {
		sql.delete("Cart.deleteItemsByMemberId", memberId);
	}
	
	
}
