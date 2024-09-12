package com.ezen.bookstore.user.products.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.orderscart.service.UserOrdersCartService;
import com.ezen.bookstore.user.products.service.UserProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/productsRest")
@RequiredArgsConstructor
public class UserProductRestController {
	
	private final UserProductService productService;
	private final UserOrdersCartService ordersCartService;
	
	@PostMapping("/productBasketSave")
	public void productBasketSave(@RequestBody List<String> book_isbn, HttpSession session) {
		ordersCartService.productBasketInsert(book_isbn, ((UserMembersDTO)session.getAttribute(AccountManagement.MEMBER_INFO)).getMember_id());
	}
}
