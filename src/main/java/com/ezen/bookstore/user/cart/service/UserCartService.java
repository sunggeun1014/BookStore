package com.ezen.bookstore.user.cart.service;

import java.util.List;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;

public interface UserCartService {

    List<UserCartDTO> getCartItemList(String memberId);
    void addCartItem(UserCartDTO userCartDTO);
    void deleteItemsByCartNums(List<Integer> cartNums, String memberId);
    boolean cartItemExists(UserCartDTO userCartDTO);
    boolean updateCartItemQuantity(UserCartDTO userCartDTO);
}
