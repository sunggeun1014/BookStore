package com.ezen.bookstore.user.mypage.review.service;

import java.util.Map;

import com.ezen.bookstore.user.mypage.review.dto.UserBookReviewDTO;

public interface UserReviewService {
	Map<String, Object> getPendingReviews(UserBookReviewDTO userBookReviewDTO, int page, int pageSize);
	Map<String, Object> getWrittenReviews(UserBookReviewDTO userBookReviewDTO, int page, int pageSize);
    UserBookReviewDTO getReviewByReviewNum(Integer reviewNum);
    void updateReview(Integer reviewNum, UserBookReviewDTO userBookReviewDTO);
    void deleteReview(Integer reviewNum);
    UserBookReviewDTO getOrderDetail(Integer orderDetailNum);
    void saveReview(UserBookReviewDTO userBookReviewDTO);
}
