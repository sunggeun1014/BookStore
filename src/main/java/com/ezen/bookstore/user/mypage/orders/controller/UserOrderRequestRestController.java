package com.ezen.bookstore.user.mypage.orders.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.service.UserOrderRequestService;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user/mypage/")
@RequiredArgsConstructor
@RestController
public class UserOrderRequestRestController {
	
	private final UserOrderRequestService orderRequestService;
	
    @PutMapping("/orderCancle")
    public int orderCancle(@RequestBody List<UserCustomerOrderWithDetailsDTO> list) {
    	return orderRequestService.orderCancle(list);
    }
    
}
