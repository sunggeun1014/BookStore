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
	
	
	public List<UserCustomerOrderWithDetailsDTO> getOrderCancleList(Integer orderNum) {
		return sql.selectList("UserOrderRequest.cancleList", orderNum);
	}
	
	public int orderCancle(Integer orderNum) {
		Integer result = sql.update("UserOrderRequest.orderCancle", orderNum);
		
		if(result != null && result > 0) {
			sql.update("UserOrderRequest.orderDateModify", orderNum);
		}
		
		return result;
	}
}
