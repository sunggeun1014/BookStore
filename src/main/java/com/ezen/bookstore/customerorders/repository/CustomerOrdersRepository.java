package com.ezen.bookstore.customerorders.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.commons.SearchConditions;
import com.ezen.bookstore.customerorders.dto.CustomerOrdersDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerOrdersRepository {
	
	private final SqlSessionTemplate sql;
	
	public List<CustomerOrdersDTO> getCustomerOrdersList(SearchConditions condition) {
		return sql.selectList("CustomerOrders.getList", condition);
	}
	

}
