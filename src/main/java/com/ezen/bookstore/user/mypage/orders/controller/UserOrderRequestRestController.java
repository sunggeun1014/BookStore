package com.ezen.bookstore.user.mypage.orders.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.mypage.orders.service.UserOrderRequestService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user/mypage")
@RequiredArgsConstructor
@RestController
public class UserOrderRequestRestController {
	
	private final UserOrderRequestService orderRequestService;

    @PutMapping("/orderCancle")
    public int orderCancle(@RequestParam Integer orderNum) {
    	return orderRequestService.orderCancle(orderNum);
    }
    
}
