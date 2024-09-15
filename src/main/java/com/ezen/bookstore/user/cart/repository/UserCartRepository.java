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
	
    public void deleteItemsByCartNums(List<Integer> cartNums) {
        sql.delete("Cart.deleteItemsByCartNums", cartNums);
    }
	
	public void deleteItemsByMemberId (String memberId) {
		sql.delete("Cart.deleteItemsByMemberId", memberId);
	}
	
	public int addCartItem(UserCartDTO dto, String memberId) {
		dto.setMember_id(memberId);
		return sql.insert("Cart.addCartItem", dto);
	}
	
    public int getBasketCount(String memberId) {
        return sql.selectOne("Cart.basketCount", memberId);
    }
	
}
