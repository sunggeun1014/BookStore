package com.ezen.bookstore.user.mypage.notice.service;

import java.util.List;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;

public interface UserNoticesService {
	
    List<UserNoticesDTO> getNoticeList(int page, int size, String keyword, UserNoticesDTO userNoticesDTO);
    int getTotalNoticesCount(String keyword);
	UserNoticesDTO getNoticesDeatil(Integer noticeNum);
	UserNoticesDTO getPreviousNoticeByNumber(Integer noticeNum, String keyword);
	UserNoticesDTO getNextNoticeByNumber(Integer noticeNum, String keyword);

}
