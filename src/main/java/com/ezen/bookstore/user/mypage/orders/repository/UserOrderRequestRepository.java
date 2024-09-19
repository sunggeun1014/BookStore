package com.ezen.bookstore.user.mypage.orders.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserOrderRequestRepository {

	private final SqlSessionTemplate sql;
		
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
