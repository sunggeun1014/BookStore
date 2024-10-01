package com.ezen.bookstore.user.products.controller;

import java.util.List;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.payment.dto.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.orderscart.service.UserOrdersCartService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Slf4j
@RestController
@RequestMapping("/user/productsRest")
@RequiredArgsConstructor
public class UserProductRestController {
	
	private final UserOrdersCartService ordersCartService;
	
	@PostMapping("/productBasketSave")
	public ResponseEntity<Integer> productBasketSave(@RequestBody List<UserCartDTO> list) {
		String memberID = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);
		String book_isbn = list.get(0).getBook_isbn();

		if (ordersCartService.existBasketItem(book_isbn, memberID) > 0) {
			for (UserCartDTO dto : list) {
				ordersCartService.updateBasketItemCnt(dto, memberID);
			}
			return ResponseEntity.ok(-1);
		}

		int result = ordersCartService.productBasketInsert(list, memberID);
		return ResponseEntity.ok(result);
	}
	
	@PostMapping("/productBasketSaveMulti")
	public Integer productBasketSaveMulti(@RequestBody List<UserCartDTO> list) {
		String memberID = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);
		
		return ordersCartService.productBasketInsert(list, memberID);
	}

	@PostMapping("/instantBuy")
	public ResponseEntity<Void> instantBuy(@RequestBody List<OrderItemDTO> items, HttpSession session) {

		session.setAttribute("orderItems", items);
		session.setAttribute("purchaseType", "instantBuy");
		return ResponseEntity.ok().build();
	}

}
