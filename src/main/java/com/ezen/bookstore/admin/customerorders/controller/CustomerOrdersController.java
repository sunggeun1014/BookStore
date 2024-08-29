package com.ezen.bookstore.admin.customerorders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/customer_orders")
public class CustomerOrdersController {
	
	private final CustomerOrdersService cos;

	@GetMapping("/list")
	public String customerOrdersList(HttpSession session) {
		session.setAttribute(AccountManagement.ADMIN_ID, "dev001");
		session.setMaxInactiveInterval(60 * 60);
		
		return "/admin/customer_orders/orders_list";
	}
	
	@GetMapping("/detail")
	public String customerOrdersList(Model model, int order_num) {
		model.addAttribute("detail", cos.getCustomerOrdersDetail(order_num));
		model.addAttribute("detailList", cos.getCustomerOrdersDetailList(order_num));
		
		return "/admin/customer_orders/orders_detail";
	}

}
