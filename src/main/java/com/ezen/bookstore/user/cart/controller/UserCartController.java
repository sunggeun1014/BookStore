package com.ezen.bookstore.user.cart.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.service.UserCartService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/cart")
public class UserCartController {

    private final UserCartService userCartService;

    @GetMapping("/list")
    public String showCart(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String memberId = (String) session.getAttribute("memberId");

        // 로그인되지 않은 경우 로그인 페이지로 리다이렉트
        if (memberId == null) {
            return "redirect:/user/login";
        }

        // 로그인된 경우 장바구니 페이지를 반환
        return "/user/main/customer_order/cart";
    }

    @PostMapping("/add")
    public void addCartItem(@RequestBody UserCartDTO cartItemDTO, @RequestParam String memberId) {
        userCartService.addCartItem(cartItemDTO, memberId);
    }

    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteCartItems(@RequestBody Map<String, Object> requestParams) {
        List<Integer> cartNums = (List<Integer>) requestParams.get("cartNums");
        userCartService.deleteItemsByCartNums(cartNums);
        return ResponseEntity.ok(Map.of("success", true));
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam String memberId) {
        userCartService.clearCart(memberId);
    }
}
