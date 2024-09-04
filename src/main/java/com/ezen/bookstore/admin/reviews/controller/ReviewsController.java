package com.ezen.bookstore.admin.reviews.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.admin.reviews.dto.ReviewsDTO;
import com.ezen.bookstore.admin.reviews.service.ReviewsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/reviews")
@Controller
public class ReviewsController {

	private final ReviewsService reviewService;

	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> tableData() {
		List<ReviewsDTO> tables = reviewService.list();

		// DataTables가 요구하는 형식으로 JSON 데이터 구성
		Map<String, Object> response = new HashMap<>();
		response.put("data", tables);
		response.put("size", tables.size());

		return response;
	}

	@PostMapping("/details")
	public String showReviewDetails(@RequestParam("review_num") Integer reviewNum, Model model) {

		ReviewsDTO reviewsDTO = reviewService.getDetailList(reviewNum);
		
		model.addAttribute("reviews", reviewsDTO);
		
		
		String templatePath = "/admin/reviews/reviewDetails";
		model.addAttribute("template", templatePath); // 경로를 template로 전달

		return "admin/index";
	}

	@PostMapping("/delete")
	public String deleteReviews(@RequestBody List<Integer> reviewIds, Model model) {
		
		reviewService.deleteReviewsById(reviewIds);
		
		String templatePath = "/admin/reviews/reviews";
		
		model.addAttribute("template", templatePath);
		
		return "admin/index";
	}

}
