package com.ezen.bookstore.admin.customerorders.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;
import com.ezen.bookstore.admin.security.service.CustomUserDetails;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/customer_orders")
public class CustomerOrdersController {
	
	private final CustomerOrdersService cos;

	@GetMapping("/list")
	public String customerOrdersList(Model model) {
		model.addAttribute("template", "/admin/customer_orders/customerList");
		
		return "/admin/index";
	}
	
	@GetMapping("/detail")
	public String customerOrdersList(Model model, int order_num, SearchCondition condition) {
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
		
		model.addAttribute("detail", cos.getCustomerOrdersDetail(order_num));
		model.addAttribute("detailList", cos.getCustomerOrdersDetailList(order_num));
		model.addAttribute("condition", condition);
		
		model.addAttribute("template", "/admin/customer_orders/customerDetail");
		
		return "/admin/index";
	}
    
    @GetMapping(value = "/orderStatusUpdate")
    public String orderStatusUpdate(@RequestParam(value = "order_detail_num") List<Integer> list, String order_detail_status, int order_num) {
    	for(int dto : list) {
    		System.out.println(dto);
    	}
    	cos.orderStatusUpdate(list, order_detail_status);
    	
    	return "redirect:/admin/customer_orders/detail?order_num=" + order_num;
    }

}
