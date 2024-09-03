package com.ezen.bookstore.admin.supplierorders.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.security.service.CustomUserDetails;
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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			String username = userDetails.getManagerName();
			
			String departmentName;
			switch (userDetails.getManagerDept()) {
			case "01":
				departmentName = "물류팀";
				break;
			case "02":
				departmentName = "운영팀";
				break;
			default:
				departmentName = "기타";
				break;
			}
			
			model.addAttribute("username", username);
			model.addAttribute("authority", departmentName);
		}
		
		model.addAttribute("template", "/admin/supplier_orders/supplierRegister");
		
		return "/admin/index";
	}
	
	@GetMapping("/detail")
	public String orderRegister(Model model, Integer order_num) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
			CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
			String username = userDetails.getManagerName();
			
			String departmentName;
			switch (userDetails.getManagerDept()) {
			case "01":
				departmentName = "물류팀";
				break;
			case "02":
				departmentName = "운영팀";
				break;
			default:
				departmentName = "기타";
				break;
			}
			
			model.addAttribute("username", username);
			model.addAttribute("authority", departmentName);
		}
		
		model.addAttribute("detail", sos.getSupplierOrdersDetail(order_num));
		model.addAttribute("detailList", sos.getSupplierOrdersDetailList(order_num));
		model.addAttribute("template", "/admin/supplier_orders/supplierDetail");
		
		return "/admin/index";
	}
	
	
}
