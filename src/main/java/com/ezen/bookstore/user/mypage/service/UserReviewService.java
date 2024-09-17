package com.ezen.bookstore.user.mypage.service;

import java.util.List;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;

public interface UserReviewService {
	List<UserBookReviewDTO> getPendingReviews(UserBookReviewDTO userBookReviewDTO);
    List<UserBookReviewDTO> getWrittenReviews(UserBookReviewDTO userBookReviewDTO);
    UserBookReviewDTO getReviewByReviewNum(Integer reviewNum);
    void updateReview(Integer reviewNum, UserBookReviewDTO userBookReviewDTO);
    void deleteReview(Integer reviewNum);
    UserBookReviewDTO getOrderDetail(Integer orderDetailNum);
    void saveReview(UserBookReviewDTO userBookReviewDTO);
}
