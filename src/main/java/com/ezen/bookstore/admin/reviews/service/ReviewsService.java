package com.ezen.bookstore.admin.reviews.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.reviews.dto.ReviewsDTO;
import com.ezen.bookstore.admin.reviews.repository.ReviewRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewsService {

	private final ReviewRepository reviewRepository;
	
	@Transactional(readOnly = true)
	public List<ReviewsDTO> list() {
		try {
			return reviewRepository.getAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	@Transactional(readOnly = true)
	public ReviewsDTO getDetailList(Integer reviewNum) {
		try {
			return reviewRepository.getDetailList(reviewNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	@Transactional
    public void deleteReviewsByIds(List<Integer> reviewIds) {
		try {
			reviewRepository.deleteAllByIdIn(reviewIds);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}

