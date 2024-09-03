package com.ezen.bookstore.admin.reviews.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.admin.reviews.dto.ReviewsDTO;

@Mapper
public interface ReviewsMapper {
	
	List<ReviewsDTO> getAll();

	ReviewsDTO getDetailList(Integer reviewNum);
	
	void deleteAllByIdIn(List<Integer> reviewIds);
	
}
