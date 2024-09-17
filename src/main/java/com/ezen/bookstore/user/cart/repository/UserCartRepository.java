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
	
	public void deleteItemsByCartNums(List<Integer> cartNums, String memberId) {
	    Map<String, Object> params = new HashMap<>();
	    params.put("cartNums", cartNums);
	    params.put("memberId", memberId);
	    sql.delete("Cart.deleteItemsByCartNums", params);
	}

	public int addCartItem(UserCartDTO userCartDTO) {
		return sql.insert("Cart.addCartItem", userCartDTO);
	}
	
	public boolean checkItemExists(UserCartDTO userCartDTO) {
		Integer count = sql.selectOne("Cart.checkItemExists", userCartDTO);
		return count != null && count > 0;
	}
	
    public int updateCartItemQuantity(UserCartDTO userCartDTO) {
        return sql.update("Cart.updateCartItemQuantity", userCartDTO);
    }
	
}
