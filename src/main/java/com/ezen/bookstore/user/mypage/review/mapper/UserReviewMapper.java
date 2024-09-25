package com.ezen.bookstore.user.mypage.review.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.mypage.review.dto.UserBookReviewDTO;

@Mapper
public interface UserReviewMapper {
    List<UserBookReviewDTO> getPendingReviews(@Param("bookReviewDTO") UserBookReviewDTO userBookReviewDTO, 
    										  @Param("startRow") int startRow,
    										  @Param("endRow") int endRow);
    int getTotalPendingReviewsCount(UserBookReviewDTO userBookReviewDTO);
    List<UserBookReviewDTO> getWrittenReviews(@Param("bookReviewDTO") UserBookReviewDTO userBookReviewDTO, 
    		 								  @Param("startRow") int startRow,
    		 								  @Param("endRow") int endRow);
    int getWrittenReviewsTotalCount(UserBookReviewDTO userBookReviewDTO);
    UserBookReviewDTO getReviewByReviewNum(Integer reviewNum);
    void updateReview(@Param("reviewNum") Integer reviewNum, @Param("userBookReviewDTO") UserBookReviewDTO userBookReviewDTO);
    void deleteReview(@Param("reviewNum") Integer reviewNum);
    UserBookReviewDTO getOrderDetail(@Param("orderDetailNum") Integer orderDetailNum);
    void insertReview(UserBookReviewDTO userBookReviewDTO);
}
