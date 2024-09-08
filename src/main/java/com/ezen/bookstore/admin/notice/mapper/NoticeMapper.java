package com.ezen.bookstore.admin.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.admin.notice.dto.NoticeDTO;

@Mapper
public interface NoticeMapper {
	List<NoticeDTO> getList();
	NoticeDTO getDetailList(Long noticeNum);
	void deleteNoticesByNums(List<Long> noticeNums);
	void insertNotice(NoticeDTO noticeDTO);
	void insertNoticeDetail(NoticeDTO noticeDTO);
	void updateNotice(NoticeDTO noticeDTO);
	void updateNoticeDetail(NoticeDTO noticeDTO);
}
