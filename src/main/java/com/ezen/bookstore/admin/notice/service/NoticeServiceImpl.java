package com.ezen.bookstore.admin.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.notice.dto.NoticeDTO;
import com.ezen.bookstore.admin.notice.mapper.NoticeMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	NoticeMapper noticeMapper;

	@Override
	public List<NoticeDTO> getList() {
		return noticeMapper.getList();
	}
	
	@Override
	public NoticeDTO getDetailList(Integer noticeNum) {
		return noticeMapper.getDetailList(noticeNum);
	}
	
	@Override
	public void deleteNoticesByNums(List<Integer> noticeNums) {
		noticeMapper.deleteNoticesByNums(noticeNums);
	}
}
