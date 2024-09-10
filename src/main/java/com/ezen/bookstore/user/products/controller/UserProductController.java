package com.ezen.bookstore.user.products.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.service.UserProductService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/products")
public class UserProductController {

	private final UserProductService productService;
	
	@GetMapping("/searchForm")
	public String ProductsSearchForm(Model model, UserSearchCondition condition) {
		
		model.addAttribute("bookList", productService.getProductList(condition));
		model.addAttribute("condition", condition);
		
	
		return "/user/main/products/searchForm";
	}
	
}
