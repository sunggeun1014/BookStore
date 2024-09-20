package com.ezen.bookstore.user.mypage.orders.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.service.UserOrderRequestService;
import com.ezen.bookstore.user.payment.dto.UserCustomerOrderDetailsDTO;

import lombok.RequiredArgsConstructor;

@RequestMapping("/user/mypage")
@RequiredArgsConstructor
@Controller
public class UserOrderRequestController {
	
	private final UserOrderRequestService orderRequestService;
	
	
	@GetMapping("/orderList")
	public String myOrderPage(Model model) {
		
		String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);

        if (memberId == null) {
            return "redirect:/user/login"; // 로그인 페이지로 리다이렉트
        }
        
		List<UserCustomerOrderWithDetailsDTO> orderList = orderRequestService.getOrderList(memberId);
		model.addAttribute("orderList",orderList);
		return "/user/mypage/customer_order/orderList";
	}
	
    @GetMapping("/cancleList")
    public String purchaseCancleForm(Model model, Integer orderNum) {
    	model.addAttribute("cancleList", orderRequestService.getOrderCancleList(orderNum)); 
    	
    	return "/user/mypage/customer_order/cancleList";
    }
    
}
