package com.ezen.bookstore.user.cart.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.service.UserCartService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Controller
@RequestMapping("/user/cart")
public class UserCartController {

	private final UserCartService userCartService;
	@GetMapping("/list")
	public String cartDetail() {
		
		return  "user/main/cart";
	}

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCartItem(@RequestBody UserCartDTO cartItemDTO) {
    	userCartService.addCartItem(cartItemDTO);
    }

    @PostMapping("/delete/{cart_num}")
    public void removeCartItem(@PathVariable Integer cartNum) {
    	userCartService.deleteCartItem(cartNum);
    }

    @PostMapping("/clear")
    public void clearCart(@RequestParam String memberId) {
    	userCartService.clearCart(memberId);
    }
}
