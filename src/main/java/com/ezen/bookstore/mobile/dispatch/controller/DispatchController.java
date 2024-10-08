package com.ezen.bookstore.mobile.dispatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.commons.CommonConstants;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/mobile/admin")
public class DispatchController {
	

	@GetMapping("/dispatch-page")
	public String getDispatchPage(Model model) {
		
		model.addAttribute("menuItems", CommonConstants.MENU_ITEMS);
		
		return "/mobile/dispatch/dispatch";
	}

	@GetMapping("/stockout-page")
	public String getStockOutPage() {
		return "/mobile/dispatch/stockout";
	}
	
	
}
