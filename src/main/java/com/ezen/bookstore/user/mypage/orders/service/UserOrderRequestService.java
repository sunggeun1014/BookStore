package com.ezen.bookstore.user.mypage.orders.service;

import java.util.List;
import java.util.Map;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;

public interface UserOrderRequestService {
	public List<UserCustomerOrderWithDetailsDTO> getOrderList();
	public Map<String, Integer> getStatusCounts();
	public List<UserCustomerOrderWithDetailsDTO> getOrderDetail(Integer orderNum);
	public List<UserCustomerOrderWithDetailsDTO> getOrderCancleList(Integer orderNum, String member_id);
	public int orderCancle(List<UserCustomerOrderWithDetailsDTO> list); 
    public UserCustomerOrderWithDetailsDTO getRefundInfo(Integer orderNum);
    public int returnRequest(Map<String, Object> data);
}
