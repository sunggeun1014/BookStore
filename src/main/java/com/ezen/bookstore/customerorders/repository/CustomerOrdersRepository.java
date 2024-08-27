package com.ezen.bookstore.customerorders.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.customerorders.dto.CustomerOrdersDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerOrdersRepository {
	
	private final SqlSessionTemplate sql;
	
	public List<CustomerOrdersDTO> getCustomerOrdersList() {
		return sql.selectList("CustomerOrders.getList");
	}
	

}
