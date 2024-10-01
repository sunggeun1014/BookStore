package com.ezen.bookstore.mobile.dispatch.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.mobile.dispatch.service.DispatchService;

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
	public String getDispatchPage() {
		return "mobile/dispatch/dispatch";
	}

	@GetMapping("/stockout-page")
	public String getStockOutPage() {
		return "mobile/dispatch/stockout";
	}
	
	
}
