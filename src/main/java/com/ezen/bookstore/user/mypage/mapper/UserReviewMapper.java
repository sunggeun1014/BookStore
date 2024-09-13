package com.ezen.bookstore.user.mypage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;

@Mapper
public interface UserReviewMapper {
	List<UserBookReviewDTO> getReviewByConditions(Map<String, Object> conditions);
}
