package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;
import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.ezen.bookstore.user.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class CompleteOrderController {

    private final PaymentService paymentService;

    @GetMapping("/complete-order")
    public String completeOrder(@RequestParam("order_num") int order_num, @RequestParam("member_id") String member_id, Model model) {
        UserOrderDTO orderDetail = paymentService.getOrder(order_num, member_id);
        List<UserOrderDetailsDTO> detailList = paymentService.getOrderDetail(order_num);

        model.addAttribute("orderDetail", orderDetail);
        model.addAttribute("detailList", detailList);

        return "/user/main/customer_order/complete-order";
    }
}
