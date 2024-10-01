package com.ezen.bookstore.user.mypage.orders.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
    @GetMapping("/statusCounts")
    public Map<String, Integer> getStatusCounts() {
        return orderRequestService.getStatusCounts();
    }

    @PutMapping("/orderCancel")
    public int orderCancel(@RequestBody List<UserCustomerOrderWithDetailsDTO> list) {
    	return orderRequestService.orderCancel(list);
    }
    
    @PostMapping("/returnRequest")
    public int returnRequest(@RequestBody Map<String, Object> data) {
    	return orderRequestService.returnRequest(data);
    }
}
