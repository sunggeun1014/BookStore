package com.ezen.bookstore.admin.reviews.service;

import java.util.List;

import com.ezen.bookstore.admin.reviews.dto.ReviewsDTO;

public interface ReviewsService {
    List<ReviewsDTO> list();
    ReviewsDTO getDetailList(Integer reviewNum);
    void deleteReviewsByIds(List<Integer> reviewIds);
}
