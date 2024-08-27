package com.ezen.bookstore.customerorders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.customerorders.service.CustomerOrdersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customer_orders")
public class CustomerOrdersController {
	
	private final CustomerOrdersService cos;

	@GetMapping("/list")
	public String customerOrdersList() {
		return "/customer_orders/orders_list";
	}

}
