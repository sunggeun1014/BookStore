package com.ezen.bookstore.admin.home.service;

import com.ezen.bookstore.admin.home.repository.HomeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class HomeService {
    private final HomeRepository homeRepository;


}
