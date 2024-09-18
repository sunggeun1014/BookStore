package com.ezen.bookstore.user.mypage.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;
import com.ezen.bookstore.user.mypage.mapper.UserReviewMapper;

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
    public List<UserBookReviewDTO> getPendingReviews(UserBookReviewDTO userBookReviewDTO) {
		userBookReviewDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
        return reviewMapper.getPendingReviews(userBookReviewDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserBookReviewDTO> getWrittenReviews(UserBookReviewDTO userBookReviewDTO) {
		userBookReviewDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
        return reviewMapper.getWrittenReviews(userBookReviewDTO);
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
