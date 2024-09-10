package com.ezen.bookstore.user.products.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.products.service.UserProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user/productsRest")
@RequiredArgsConstructor
public class UserProductRestController {
	
	private final UserProductService productService;

	
}
