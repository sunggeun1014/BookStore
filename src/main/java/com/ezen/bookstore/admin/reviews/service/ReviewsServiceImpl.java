package com.ezen.bookstore.admin.reviews.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.reviews.dto.ReviewsDTO;
import com.ezen.bookstore.admin.reviews.mapper.ReviewsMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReviewsServiceImpl implements ReviewsService {

    ReviewsMapper reviewsMapper;
    
    @Override
    @Transactional(readOnly = true)
    public List<ReviewsDTO> list() {
    	return reviewsMapper.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public ReviewsDTO getDetailList(Integer reviewNum) {
    	return reviewsMapper.getDetailList(reviewNum);
    }

    @Override
    @Transactional
    public void deleteReviewsById(List<Integer> reviewIds) {
    	reviewsMapper.deleteAllByIdIn(reviewIds);
    }
}