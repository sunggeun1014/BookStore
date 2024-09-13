package com.ezen.bookstore.user.mypage.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;
import com.ezen.bookstore.user.mypage.mapper.UserReviewMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserReviewServiceImpl implements UserReviewService {
	UserReviewMapper reviewMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<UserBookReviewDTO> getReviewById(Map<String, Object> conditions) {
		return reviewMapper.getReviewByConditions(conditions);
	}
}
