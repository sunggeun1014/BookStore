package com.ezen.bookstore.user.mypage.orders.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.mypage.orders.service.UserOrderRequestService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user/mypage")
@RequiredArgsConstructor
@Controller
public class UserOrderRequestController {
	
	private final UserOrderRequestService orderRequestService;
	
    @GetMapping("/cancleList")
    public String purchaseCancleForm(Model model, Integer orderNum) {
    	model.addAttribute("cancleList", orderRequestService.getOrderCancleList(orderNum)); 
    	
    	return "/user/mypage/customer_order/cancleList";
    }
    
}
