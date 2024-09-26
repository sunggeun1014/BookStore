package com.ezen.bookstore.user.mypage.orders.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserOrderRequestRepository {

	private final SqlSessionTemplate sql;
		
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderList(String memberId) {
		return sql.selectList("UserOrderRequest.orderList", memberId);
		
	};
	
    public int countByOrderStatus(String memberId, String status) {
        Map<String, String> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("status", status);
        return sql.selectOne("UserOrderRequest.countByOrderStatus", params);
    }

    public int countByDeliveryStatus(String memberId, String status) {
        Map<String, String> params = new HashMap<>();
        params.put("memberId", memberId);
        params.put("status", status);
    	return sql.selectOne("UserOrderRequest.countByDeliveryStatus", params);
    }
	
    public List<UserCustomerOrderWithDetailsDTO> getDetailItem(Integer orderNum) {
        return sql.selectList("UserOrderRequest.getDetailItem", orderNum);
    }
    
    public UserCustomerOrderWithDetailsDTO getOrderDetail(Integer orderNum) {
    	return sql.selectOne("UserOrderRequest.getOrderDetail", orderNum);
    }
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderCancleList(Integer orderNum, String memberId) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("orderNum", orderNum);
		map.put("memberId", memberId);
		
		return sql.selectList("UserOrderRequest.cancleList", map);
	}
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderReturnList(Integer orderNum, String memberId) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("orderNum", orderNum);
		map.put("memberId", memberId);
		
		return sql.selectList("UserOrderRequest.returnList", map);
	}
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderRequestList(Integer orderNum, String memberId) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("orderNum", orderNum);
		map.put("memberId", memberId);
		
		return sql.selectList("UserOrderRequest.orderRequestList", map);
	}
	
	public int orderCancle(UserCustomerOrderWithDetailsDTO dto) {
		Integer result = sql.update("UserOrderRequest.orderCancle", dto);
		
		if(result != null && result > 0) {
			sql.update("UserOrderRequest.orderDateModify", dto.getOrder_num());
		}
		
		return result;
	}
	
	public UserCustomerOrderWithDetailsDTO getRefundInfo(Integer orderNum) {
		return sql.selectOne("UserOrderRequest.getRefundInfo", orderNum);
	}
	
	public UserCustomerOrderWithDetailsDTO getReturnRefundInfo(Integer orderNum) {
		return sql.selectOne("UserOrderRequest.getReturnRefundInfo", orderNum);
	}
	
	public int returnRequest(Map<String, Integer> map) {
		return sql.update("UserOrderRequest.returnRequest", map);
	}
	
	public void returnRequestInfoUpdate(Map<String, String> info) {
		sql.update("UserOrderRequest.returnRequestInfoUpdate", info);
	}
	
}
