package com.ezen.bookstore.admin.customerorders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersListDTO;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/customerOrders")
public class CustomerOrdersController {
	
	private final CustomerOrdersService cos;

	@GetMapping("/list")
	public String customerOrdersList(Model model) {
		model.addAttribute("template", "/admin/customer_orders/customerList");
		
		return "/admin/index";
	}
	
	@GetMapping("/detail")
	public String customerOrdersList(Model model, int order_num, SearchCondition condition) {
		model.addAttribute("detail", cos.getCustomerOrdersDetail(order_num));
		model.addAttribute("detailList", cos.getCustomerOrdersDetailList(order_num));
		model.addAttribute("condition", condition);
		
		model.addAttribute("template", "/admin/customer_orders/customerDetail");
		
		return "/admin/index";
	}
    
    @GetMapping(value = "/orderStatusUpdate")
    public String orderStatusUpdate(@ModelAttribute CustomerOrdersListDTO list, int order_num, String order_selected_status) {
    	System.out.println("asdffasdfdsafsdafsdaasfafsd");
    	cos.orderStatusUpdate(list, order_num, order_selected_status);
    	
    	return "redirect:/admin/customerOrders/detail?order_num=" + order_num;
    }

}