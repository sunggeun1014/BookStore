package com.ezen.bookstore.user.mypage.orders.service;

import java.util.List;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;

public interface UserOrderRequestService {
	public List<UserCustomerOrderWithDetailsDTO> getOrderList(String memberId);
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderCancleList(Integer orderNum);
	public int orderCancle(Integer orderNum); 
}
