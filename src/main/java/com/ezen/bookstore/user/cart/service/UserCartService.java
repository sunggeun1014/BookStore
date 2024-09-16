package com.ezen.bookstore.user.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.repository.UserCartRepository;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserCartService {

	private final UserCartRepository userCartRepository;

	public List<UserCartDTO> getCartItemList(String memberId) {
		return userCartRepository.getCartItemList(memberId);
	}

	public void addCartItem(UserCartDTO userCartDTO) {
		String memberId = userCartDTO.getMember_id();
		userCartDTO.setMember_id(memberId);
		
		// 장바구니에 이미 항목이 있는지 확인
		boolean exists = userCartRepository.checkItemExists(userCartDTO);

		if (exists) {
			// 이미 있는 항목의 수량을 업데이트
			userCartRepository.updateCartItemQuantity(userCartDTO);
		} else {
			// 새로운 항목 추가
			userCartRepository.addCartItem(userCartDTO);
		}
	}

	public void deleteItemsByCartNums(List<Integer> cartNums, String memberId) {
		userCartRepository.deleteItemsByCartNums(cartNums, memberId);
	}

}
