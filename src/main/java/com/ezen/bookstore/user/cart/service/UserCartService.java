package com.ezen.bookstore.user.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.repository.UserCartRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserCartService {

	private final UserCartRepository userCartRepository;
	
	public List<UserCartDTO> getCartItemList(String memberId) {
		return userCartRepository.getCartItemList(memberId);
	}

	public void addCartItem(UserCartDTO userCartDTO) {
		userCartRepository.addCartItem(userCartDTO);
	}
	
	public void deleteCartItem(Integer cartNum) {
		userCartRepository.deleteItemByCartNum(cartNum);
	}
	
	public void clearCart(String memberId) {
		userCartRepository.deleteItemsByMemberId(memberId);
	}
 }
