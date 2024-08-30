package com.ezen.bookstore.admin.managers.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.managers.repository.MgrRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MgrService {
	private final  MgrRepository mgrRepository;
	
	public List<ManagersDTO> list(){
		// DB에서 모든 주문 목록을 꺼내와야 한다
		return mgrRepository.getMembers();
	}
	
	public ManagersDTO detailList(String managerId) {
		return mgrRepository.getManagerDetails(managerId);
	}
}
