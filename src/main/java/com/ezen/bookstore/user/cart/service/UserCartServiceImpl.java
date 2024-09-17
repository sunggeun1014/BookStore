package com.ezen.bookstore.user.cart.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.repository.UserCartRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class UserCartServiceImpl implements UserCartService {

    private final UserCartRepository userCartRepository;

    @Override
    public List<UserCartDTO> getCartItemList(String memberId) {
        return userCartRepository.getCartItemList(memberId);
    }

    @Override
    public void addCartItem(UserCartDTO userCartDTO) {
        userCartRepository.addCartItem(userCartDTO);
    }

    @Override
    public void deleteItemsByCartNums(List<Integer> cartNums, String memberId) {
        userCartRepository.deleteItemsByCartNums(cartNums, memberId);
    }

    @Override
    public boolean cartItemExists(UserCartDTO userCartDTO) {
        return userCartRepository.checkItemExists(userCartDTO);
    }
}
