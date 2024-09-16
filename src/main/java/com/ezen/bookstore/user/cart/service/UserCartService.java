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

	public void addCartItem(UserCartDTO userCartDTO, HttpSession session) {
		String memberId = (String) session.getAttribute("memberId");
		userCartDTO.setMember_id(memberId);
		userCartRepository.addCartItem(userCartDTO, memberId);
	}

	public void deleteItemsByCartNums(List<Integer> cartNums, String memberId) {
	    userCartRepository.deleteItemsByCartNums(cartNums, memberId);
	}

}
