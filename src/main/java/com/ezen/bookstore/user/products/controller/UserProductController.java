package com.ezen.bookstore.user.products.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.commons.Pagination;
import com.ezen.bookstore.commons.PaginationProcess;
import com.ezen.bookstore.user.bookcategory.service.UserBookCategoryService;
import com.ezen.bookstore.user.commons.UserSearchCondition;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.products.dto.UserProductDTO;
import com.ezen.bookstore.user.products.dto.UserReviewDTO;
import com.ezen.bookstore.user.products.service.UserProductService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/products")
public class UserProductController {

	private final UserProductService productService;
	private final UserBookCategoryService bookCategoryService;
	private final PaginationProcess paginationProcess;
	
	@GetMapping("/searchForm")
	public String ProductsSearchForm(Model model, UserSearchCondition condition, Pagination pagination) {
		List<UserProductDTO> productList = productService.getProductList(condition);
		
		model.addAttribute("categoryList", bookCategoryService.getCategoryList(condition));
		model.addAttribute("condition", condition);
		
		Map<String, Object> map = paginationProcess.process(pagination, productList);
		model.addAttribute("page", map.get("page"));
		model.addAttribute("productList", map.get("list"));
	
		return "/user/main/products/searchForm";
	}

	@GetMapping("/detail")
	public String productDetail(@RequestParam("book_isbn") String bookISBN, Model model, HttpSession session, Pagination pagination) {
		UserMembersDTO memberDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);

		String memberID = memberDTO != null ? memberDTO.getMember_id() : null;

		UserProductDTO bookDetail = productService.getProductDetail(bookISBN);
		List<UserReviewDTO> reviewList = productService.getReviewList(bookISBN);
		List<UserReviewDTO> reviewPercent = productService.getReviewPercent(bookISBN);

		if (reviewList != null && !reviewList.isEmpty()) {
//			model.addAttribute("reviewList", reviewList);
			Map<String, Object> map = paginationProcess.process(pagination, reviewList);
			model.addAttribute("page", map.get("page"));
			model.addAttribute("reviewList", map.get("list"));
		}

		model.addAttribute("member_id", memberID);
		model.addAttribute("bookDetail", bookDetail);
		model.addAttribute("reviewPercent", reviewPercent);

		return "/user/main/products/detail";
	}
	
}
