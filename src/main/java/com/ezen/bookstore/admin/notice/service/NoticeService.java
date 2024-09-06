package com.ezen.bookstore.admin.notice.service;

import java.util.List;

import com.ezen.bookstore.admin.notice.dto.NoticeDTO;

public interface NoticeService {
	List<NoticeDTO> getList();
	NoticeDTO getDetailList(Integer noticeNum);
	void deleteNoticesByNums(List<Integer> noticeNums);
}
