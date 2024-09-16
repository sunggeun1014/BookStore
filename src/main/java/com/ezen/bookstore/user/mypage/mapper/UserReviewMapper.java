package com.ezen.bookstore.user.mypage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;

@Mapper
public interface UserReviewMapper {
    List<UserBookReviewDTO> getPendingReviews(UserBookReviewDTO userBookReviewDTO);
    List<UserBookReviewDTO> getWrittenReviews(UserBookReviewDTO userBookReviewDTO);
    UserBookReviewDTO getReviewByReviewNum(Long reviewNum);
    void updateReview(@Param("reviewNum") Long reviewNum, @Param("userBookReviewDTO") UserBookReviewDTO userBookReviewDTO);
    void deleteReview(@Param("reviewNum") Long reviewNum);
    UserBookReviewDTO getOrderDetail(@Param("orderDetailNum") Integer orderDetail);
}
