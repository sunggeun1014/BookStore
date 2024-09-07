package com.ezen.bookstore.admin.customerorders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersListDTO;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/customerOrders")
public class CustomerOrdersController {
	
	private final CustomerOrdersService cos;

	@GetMapping("/list")
	public String customerOrdersList() {
		
		return "admin/customer_orders/customerList";
	}
	
	@GetMapping("/detail")
	public String customerOrdersList(Model model, int order_num, SearchCondition condition) {
		model.addAttribute("detail", cos.getCustomerOrdersDetail(order_num));
		model.addAttribute("detailList", cos.getCustomerOrdersDetailList(order_num));
		model.addAttribute("condition", condition);
		
		
		return "admin/customer_orders/customerDetail";
	}
    
}