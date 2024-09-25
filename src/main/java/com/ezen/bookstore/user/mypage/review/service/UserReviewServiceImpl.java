package com.ezen.bookstore.user.mypage.review.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.mypage.review.dto.UserBookReviewDTO;
import com.ezen.bookstore.user.mypage.review.mapper.UserReviewMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;


@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserReviewServiceImpl implements UserReviewService {
	UserReviewMapper reviewMapper;
	
	@Override
    @Transactional(readOnly = true)
    public Map<String, Object> getPendingReviews(UserBookReviewDTO userBookReviewDTO, int page, int pageSize) {
		userBookReviewDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;
        
        List<UserBookReviewDTO> bookReviewList = reviewMapper.getPendingReviews(userBookReviewDTO, startRow, endRow);
		int totalCount = reviewMapper.getTotalPendingReviewsCount(userBookReviewDTO);
		
		int totalPages = (int) Math.ceil((double) totalCount / pageSize);
		Map<String, Object> result = new HashMap<>();
		result.put("reviews", bookReviewList);
		result.put("totalPages", totalPages);
		
		return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getWrittenReviews(UserBookReviewDTO userBookReviewDTO, int page, int pageSize) {
		userBookReviewDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
        int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;
        
        List<UserBookReviewDTO> bookReviewList = reviewMapper.getWrittenReviews(userBookReviewDTO, startRow, endRow);
        int totalCount = reviewMapper.getWrittenReviewsTotalCount(userBookReviewDTO);
        
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        Map<String, Object> result = new HashMap<>();
		result.put("reviews", bookReviewList);
		result.put("totalPages", totalPages);
		
		return result;
    }

    @Override
    @Transactional(readOnly = true)
    public UserBookReviewDTO getReviewByReviewNum(Integer reviewNum) {
        return reviewMapper.getReviewByReviewNum(reviewNum);
    }

    @Override
    public void updateReview(Integer reviewNum, UserBookReviewDTO userBookReviewDTO) {
        reviewMapper.updateReview(reviewNum, userBookReviewDTO);
    }

    @Override
    public void deleteReview(Integer reviewNum) {
        reviewMapper.deleteReview(reviewNum);
    }
    
    @Override
    @Transactional(readOnly = true)
    public UserBookReviewDTO getOrderDetail(Integer orderDetailNum) {
    	return reviewMapper.getOrderDetail(orderDetailNum);
    }
    
    @Override
    public void saveReview(UserBookReviewDTO userBookReviewDTO) {
    	userBookReviewDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
    	reviewMapper.insertReview(userBookReviewDTO);
    }
}
