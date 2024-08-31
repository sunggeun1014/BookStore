package com.ezen.bookstore.admin.customerorders.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
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
		// 테스트용 세션
		session.setAttribute(AccountManagement.ADMIN_ID, "dev001");
		session.setMaxInactiveInterval(60 * 60);
		
		return "/admin/customer_orders/orders_list";
	}
	
	@GetMapping("/detail")
	public String customerOrdersList(Model model, int order_num) {
		model.addAttribute("detail", cos.getCustomerOrdersDetail(order_num));
		model.addAttribute("detailList", cos.getCustomerOrdersDetailList(order_num));

		model.addAttribute("template", "/admin/customer_orders/orders_detail");
		
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