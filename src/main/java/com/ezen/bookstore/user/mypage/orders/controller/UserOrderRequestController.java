package com.ezen.bookstore.user.mypage.orders.controller;

import java.util.List;
import java.util.Map;

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
		List<UserCustomerOrderWithDetailsDTO> orderList = orderRequestService.getOrderList();
		model.addAttribute("orderList", orderList);

		Map<String, Integer> orderStatusCount = orderRequestService.getOrderStatusCounts();
		model.addAttribute("orderStatusCount", orderStatusCount);

		Map<String, Integer> deliveryStatusCount = orderRequestService.getDeliveryStatusCounts();
		model.addAttribute("deliveryStatusCount", deliveryStatusCount);

		return "/user/mypage/customer_order/orderList";
	}

	@GetMapping("/orderDetail")
	public String myOrderDetail(Model model, @RequestParam("orderNum") Integer orderNum) {
		UserCustomerOrderWithDetailsDTO orderDetails = orderRequestService.getOrderDetail(orderNum);
		model.addAttribute("orderDetails", orderDetails);

		return "/user/mypage/customer_order/orderDetail";
	}

	@GetMapping("/cancleList")
	public String purchaseCancleForm(Model model, Integer orderNum, HttpSession session) {
		model.addAttribute("cancleList", orderRequestService.getOrderCancleList(orderNum,
				((UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO)).getMember_id()));

		return "/user/mypage/customer_order/cancleList";
	}

	@GetMapping("/cancleCompletion")
	public String purchaseCancleCompletionForm(Model model, Integer orderNum, HttpSession session) {
		model.addAttribute("cancleList", orderRequestService.getOrderCancleList(orderNum,
				((UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO)).getMember_id()));
		model.addAttribute("refund", orderRequestService.getRefundInfo(orderNum));

		return "/user/mypage/customer_order/cancleCompletion";
	}

}
