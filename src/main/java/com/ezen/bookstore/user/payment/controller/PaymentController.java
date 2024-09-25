package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.payment.dto.OrderItemDTO;
import com.ezen.bookstore.user.payment.service.PaymentService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/order")
    public String payment(HttpSession session, Model model) {
        UserMembersDTO memberDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);

        if (memberDTO != null) {
            model.addAttribute("memberDTO", memberDTO);
        }

        String purchaseType = (String) session.getAttribute("purchaseType");

        List<OrderItemDTO> items = (List<OrderItemDTO>) session.getAttribute("orderItems");
        List<OrderItemDTO> cartItems = (List<OrderItemDTO>) session.getAttribute("orderCartItems");

        Integer totalItems = 0;
        Integer totalPrice = 0;

        log.info("받은 목록: {}", items);
        log.info("받은 카트목록: {}", cartItems);
        log.info("받은 타입: {}", purchaseType);

        List<OrderItemDTO> combinedItems = new ArrayList<>();
        if (items != null) {
            combinedItems.addAll(items);
        }

        if (cartItems != null) {
            combinedItems.addAll(cartItems);
        }

        if (!combinedItems.isEmpty()) {
            for (OrderItemDTO item : combinedItems) {
                totalItems += item.getCart_purchase_qty();
                totalPrice += item.getBook_price();
            }

            model.addAttribute("combinedItems", combinedItems);
            model.addAttribute("itemsCnt", totalItems);
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("error", "데이터를 찾을 수 없습니다");
        }

//        session.removeAttribute("purchaseType");

        return "/user/main/customer_order/payment";
    }

}
