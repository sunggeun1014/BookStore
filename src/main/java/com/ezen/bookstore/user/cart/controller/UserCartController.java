package com.ezen.bookstore.user.cart.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		
		return  "/user/main/cart";
	}

//	@GetMapping("/list")
//	public String cartDetail(@RequestParam String memberId, Model model) {
//	    List<UserCartDTO> cartItems = userCartService.getCartItemList(memberId);
//	    
//	    model.addAttribute("cartItems", cartItems);
//	    
//	    return "/user/main/cart";
//	}
	
    @PostMapping("/add")
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
