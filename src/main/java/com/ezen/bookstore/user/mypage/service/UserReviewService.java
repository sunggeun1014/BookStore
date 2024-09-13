package com.ezen.bookstore.user.mypage.service;

import java.util.List;
import java.util.Map;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;

public interface UserReviewService {
	List<UserBookReviewDTO> getReviewById(Map<String, Object> conditions);
}
