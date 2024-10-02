package com.ezen.bookstore.user.mypage.orders.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.service.UserOrderRequestService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequestMapping("/user/mypage")
@RequiredArgsConstructor
@Controller
public class UserOrderRequestController {

	private final UserOrderRequestService orderRequestService;

	@GetMapping("/orderList")
	public String myOrderPage(Model model) {
        model.addAttribute("orderList", orderRequestService.getOrderList());
		return "/user/mypage/customer_order/orderList";
	}

	@GetMapping("/orderDetail")
	public String myOrderDetail(@RequestParam("orderNum") Integer orderNum, Model model) {
		UserCustomerOrderWithDetailsDTO orderDetails = orderRequestService.getOrderDetail(orderNum);
		List<UserCustomerOrderWithDetailsDTO> detailList  = orderRequestService.getDetailItem(orderNum);
		
		model.addAttribute("detailList", detailList);
	    model.addAttribute("orderDetails", orderDetails);
	    
	    return "/user/mypage/customer_order/orderDetail";
	}

    @GetMapping("/cancelList")
    public String purchaseCancelForm(Model model, Integer orderNum, HttpSession session) {
    	System.out.println("asdfasdfasdfa");
    	model.addAttribute("cancelList", orderRequestService.getOrderRequestList(orderNum, ((UserMembersDTO)session.getAttribute(AccountManagement.MEMBER_INFO)).getMember_id()));
    	
    	return "/user/mypage/customer_order/cancelList";
    }
    
    @GetMapping("/cancelCompletion")
    public String purchaseCancelCompletionForm(Model model, Integer orderNum) {
        model.addAttribute("cancelList", orderRequestService.getOrderCancelList(orderNum));
        model.addAttribute("refund", orderRequestService.getRefundInfo(orderNum));
        
        return "/user/mypage/customer_order/cancelCompletion";
    }
    
    @GetMapping("/returnRequest")
    public String returnRequestForm(Model model, Integer orderNum, HttpSession session) {
    	model.addAttribute("cancelList", orderRequestService.getOrderRequestList(orderNum, ((UserMembersDTO)session.getAttribute(AccountManagement.MEMBER_INFO)).getMember_id()));
    	model.addAttribute("refund", orderRequestService.getRefundInfo(orderNum));
    	
    	return "/user/mypage/customer_order/returnRequest";
    }
    
    @GetMapping("/returnDetail")
    public String returnDetail(Model model, Integer orderNum) {
    	model.addAttribute("cancelList", orderRequestService.getOrderReturnList(orderNum));
    	model.addAttribute("refund", orderRequestService.getReturnRefundInfo(orderNum));
    	
    	return "/user/mypage/customer_order/returnDetail";
    }
}
