package com.ezen.bookstore.user.mypage.notice.service;

import java.util.List;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;

public interface UserNoticesService {
	List<UserNoticesDTO> getNoticeList(UserNoticesDTO userNoticesDTO); 
	UserNoticesDTO getNoticesDeatil(Integer noticeNum);
	UserNoticesDTO getPreviousNoticeByNumber(Integer noticeNum);
	UserNoticesDTO getNextNoticeByNumber(Integer noticeNum);

}
