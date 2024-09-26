package com.ezen.bookstore.user.mypage.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;
import com.ezen.bookstore.user.mypage.notice.mapper.UserNoticesMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserNoticesServiceImpl implements UserNoticesService{
	
	UserNoticesMapper noticesMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<UserNoticesDTO> getNoticeList(int page, int size, String keyword, UserNoticesDTO userNoticesDTO) {
	    int startRow = page * size + 1;  
	    int endRow = (page + 1) * size;  
	    return noticesMapper.getNoticesListWithPaging(userNoticesDTO, startRow, endRow, "%" + keyword + "%"); // 검색어 전달
	}

	@Override
	public int getTotalNoticesCount(String keyword) {
	    return noticesMapper.getTotalNoticesCount("%" + keyword + "%"); 
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserNoticesDTO getNoticesDeatil(Integer noticeNum) {
		return noticesMapper.getNoticesDetail(noticeNum);
		
	}

	@Override
	@Transactional(readOnly = true)
	public UserNoticesDTO getPreviousNoticeByNumber(Integer noticeNum, String keyword) {
	    return noticesMapper.getPreviousNotice(noticeNum, "%" + keyword + "%");
	}

	@Override
	@Transactional(readOnly = true)
	public UserNoticesDTO getNextNoticeByNumber(Integer noticeNum, String keyword) {
	    return noticesMapper.getNextNotice(noticeNum, "%" + keyword + "%");
	}

}
