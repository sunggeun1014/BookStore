package com.ezen.bookstore.user.products.controller;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.products.dto.UserProductDTO;
import com.ezen.bookstore.user.products.dto.UserReviewDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.bookcategory.service.UserBookCategoryService;
import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.products.service.UserProductService;

import lombok.RequiredArgsConstructor;

import java.util.Collections;
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
	public String productDetail(String bookISBN, Model model, HttpSession session) {
		UserMembersDTO member = (UserMembersDTO)session.getAttribute(AccountManagement.MEMBER_INFO);

		String memID = member != null ? member.getMember_id() : null;

		bookISBN = "9791172100650";

		UserProductDTO bookDetail = productService.getProductDetail(bookISBN);
		List<UserReviewDTO> reviewList = productService.getReviewList(bookISBN);
		List<UserReviewDTO> reviewPercent = productService.getReviewPercent(bookISBN);

		if (reviewList != null && !reviewList.isEmpty()) {
			model.addAttribute("reviewList", reviewList);
		}

		model.addAttribute("memID", memID);
		model.addAttribute("bookDetail", bookDetail);
		model.addAttribute("reviewPercent", reviewPercent);

		return "/user/main/products/detail";
	}
	
}
