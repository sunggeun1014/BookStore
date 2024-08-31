package com.ezen.bookstore.admin.customerorders.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerOrdersRepository {
	
	private final SqlSessionTemplate sql;
	
	public List<CustomerOrdersDTO> getCustomerOrdersList() {
		return sql.selectList("CustomerOrders.getList");
	}
	
	public List<CustomerOrdersDTO> getDataFilterList(SearchCondition condition) {
		return sql.selectList("CustomerOrders.getList", condition);
	}
	
	public int deliveryRequestSave(String manager_id) {
		return sql.insert("CustomerOrders.deliveryRequestSave", manager_id);
	}
	
	public int deliveryDetailSave(int order_num) {
		return sql.insert("CustomerOrders.deliveryDetailSave", order_num);
	}
	
	public CustomerOrdersDTO getCustomerOrdersDetail(int order_num) {
		return sql.selectOne("CustomerOrders.getDetail", order_num);
	}
	
	public List<CustomerOrdersDTO> getCustomerOrdersDetailList(int order_num) {
		return sql.selectList("CustomerOrders.getDetailList", order_num);
	}
	
	public int orderStatusUpdate(int order_detail_num, String order_detail_status) {
		Map<String, Object> map = new HashMap<>();
		
		map.put("order_detail_num", order_detail_num);
		map.put("order_detail_status", order_detail_status);
		
		return sql.update("CustomerOrders.orderStatusUpdate", map);
	}

}