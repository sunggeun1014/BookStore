package com.ezen.bookstore.admin.supplierorders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.supplierorders.service.SupplierOrdersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/supplier_orders")
public class SupplierOrdersController {
	
	private final SupplierOrdersService sos;
	
	@GetMapping("/list")
	public String supplierOrdersList(Model model) {
		model.addAttribute("template", "/admin/supplier_orders/supplierList");
		
		return "/admin/index";
	}
	
	@GetMapping("/register")
	public String orderRegister(Model model) {
		model.addAttribute("template", "/admin/supplier_orders/supplierRegister");
		
		return "/admin/index";
	}
}
