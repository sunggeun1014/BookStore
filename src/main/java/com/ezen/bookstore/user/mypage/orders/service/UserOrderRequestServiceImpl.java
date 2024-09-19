package com.ezen.bookstore.user.mypage.orders.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.repository.UserOrderRequestRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserOrderRequestServiceImpl implements UserOrderRequestService {
	
	private final UserOrderRequestRepository orderRequestRepository;
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderCancleList(Integer orderNum) {
		try {
			return orderRequestRepository.getOrderCancleList(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	@Transactional
	public int orderCancle(Integer orderNum) {
		int result = 0;
		
		try {
			result = orderRequestRepository.orderCancle(orderNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

}
