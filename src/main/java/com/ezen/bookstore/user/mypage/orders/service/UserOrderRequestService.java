package com.ezen.bookstore.user.mypage.orders.service;

import java.util.List;
import java.util.Map;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.dto.UserProductRequestDTO;

public interface UserOrderRequestService {
	public List<UserCustomerOrderWithDetailsDTO> getOrderList();
	public Map<String, Integer> getStatusCounts();
	public UserCustomerOrderWithDetailsDTO getOrderDetail(Integer orderNum);
	public List<UserCustomerOrderWithDetailsDTO> getDetailItem(Integer orderNum);
	public List<UserProductRequestDTO> getOrderCancelList(Integer orderNum);
	public List<UserProductRequestDTO> getOrderReturnList(Integer orderNum);
	public List<UserCustomerOrderWithDetailsDTO> getOrderRequestList(Integer orderNum, String member_id);
	public int orderCancel(List<UserCustomerOrderWithDetailsDTO> list); 
    public UserCustomerOrderWithDetailsDTO getRefundInfo(Integer orderNum);
    public UserCustomerOrderWithDetailsDTO getReturnRefundInfo(Integer orderNum);
    public int returnRequest(Map<String, Object> data);
}
