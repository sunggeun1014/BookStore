package com.ezen.bookstore.user.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.user.main.repository.UserMainRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserMainService {
	private final UserMainRepository userMainRepository;


    public List<ProductsDTO> getNewBooks() {
        return userMainRepository.getNewBooks();
    }

}
