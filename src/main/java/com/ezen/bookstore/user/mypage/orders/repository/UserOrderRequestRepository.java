package com.ezen.bookstore.user.mypage.orders.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.user.mypage.orders.dto.UserCustomerOrderWithDetailsDTO;
import com.ezen.bookstore.user.mypage.orders.dto.UserProductRequestDTO;

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
	
	public List<UserProductRequestDTO> getOrderCancleList(Integer orderNum) {
		return sql.selectList("ProductRequest.getRequestList", orderNum);
	}
	
	public List<UserProductRequestDTO> getOrderReturnList(Integer orderNum) {
		return sql.selectList("ProductRequest.getRefundRequestList", orderNum);
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
	
	public int returnRequest(Map<String, Object> map) {
		return sql.update("UserOrderRequest.returnRequest", map);
	}
	
	public void returnRequestInfoUpdate(Map<String, String> info) {
		sql.update("UserOrderRequest.returnRequestInfoUpdate", info);
	}
	
	public void productRequestInsert(UserCustomerOrderWithDetailsDTO dto) {
		sql.update("ProductRequest.productRequestInsert", dto);
	}
	
	public void productRequestInsert(String order_detail_status, Integer order_detail_qty, Integer order_detail_num) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("order_detail_status", order_detail_status);
		map.put("order_detail_qty", order_detail_qty);
		map.put("order_detail_num", order_detail_num);
		
		sql.update("ProductRequest.adminProductRequestInsert", map);
	}
}
