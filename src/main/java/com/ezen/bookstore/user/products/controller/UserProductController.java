package com.ezen.bookstore.user.products.controller;

import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.user.products.dto.UserProductDTO;
import com.ezen.bookstore.user.products.dto.UserReviewDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.bookcategory.service.UserBookCategoryService;
import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.service.UserProductService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/products")
public class UserProductController {

	private final UserProductService productService;
	private final UserBookCategoryService bookCategoryService;
	
	@GetMapping("/searchForm")
	public String ProductsSearchForm(Model model, UserSearchCondition condition) {
		
		model.addAttribute("productList", productService.getProductList(condition));
		model.addAttribute("categoryList", bookCategoryService.getCategoryList(condition));
		model.addAttribute("condition", condition);
		
	
		return "/user/main/products/searchForm";
	}

	@GetMapping("/detail")
	public String productDetail(String bookISBN, Model model) {

		bookISBN = "9791139220704";

		UserProductDTO bookDetail = productService.getProductDetail(bookISBN);
		List<UserReviewDTO> reviewList = productService.getReviewList(bookISBN);

		model.addAttribute("bookDetail", bookDetail);
		model.addAttribute("reviewList", reviewList);

		return "/user/main/products/detail";
	}
	
}
