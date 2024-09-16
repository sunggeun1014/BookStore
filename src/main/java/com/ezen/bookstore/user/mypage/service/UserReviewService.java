package com.ezen.bookstore.user.mypage.service;

import java.util.List;
import java.util.Map;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;

public interface UserReviewService {
	List<UserBookReviewDTO> getPendingReviews(UserBookReviewDTO userBookReviewDTO);
    List<UserBookReviewDTO> getWrittenReviews(UserBookReviewDTO userBookReviewDTO);
    UserBookReviewDTO getReviewByReviewNum(Long reviewNum);
    void updateReview(Long reviewNum, UserBookReviewDTO userBookReviewDTO);
    void deleteReview(Long reviewNum);
    UserBookReviewDTO getOrderDetail(Integer orderDetailNum);
}
