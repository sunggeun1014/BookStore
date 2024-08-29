package com.ezen.bookstore.admin.customerorders.repository;

import java.util.List;

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
	

}
