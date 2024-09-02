package com.ezen.bookstore.admin.reviews.repository;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.reviews.dto.ReviewsDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class ReviewRepository {

	private final SqlSessionTemplate sql;

	public List<ReviewsDTO> getAll() {

		return sql.selectList("Reviews.getAll");
	}
	
	public ReviewsDTO getDetailList(Integer reviewNum) {
		return sql.selectOne("Reviews.getDetailList", reviewNum);
	}
	
	public void deleteAllByIdIn(List<Integer> reviewIds) {
		sql.delete("Reviews.deleteReviews", reviewIds);
	}
}

