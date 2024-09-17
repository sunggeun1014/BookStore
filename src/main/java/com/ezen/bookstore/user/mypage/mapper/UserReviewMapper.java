package com.ezen.bookstore.user.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;

@Mapper
public interface UserReviewMapper {
    List<UserBookReviewDTO> getPendingReviews(UserBookReviewDTO userBookReviewDTO);
    List<UserBookReviewDTO> getWrittenReviews(UserBookReviewDTO userBookReviewDTO);
    UserBookReviewDTO getReviewByReviewNum(Integer reviewNum);
    void updateReview(@Param("reviewNum") Integer reviewNum, @Param("userBookReviewDTO") UserBookReviewDTO userBookReviewDTO);
    void deleteReview(@Param("reviewNum") Integer reviewNum);
    UserBookReviewDTO getOrderDetail(@Param("orderDetailNum") Integer orderDetailNum);
    void insertReview(UserBookReviewDTO userBookReviewDTO);
}
