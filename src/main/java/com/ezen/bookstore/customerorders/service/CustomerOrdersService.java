package com.ezen.bookstore.customerorders.service;

import java.util.List;

import com.ezen.bookstore.commons.SearchCondition;
import com.ezen.bookstore.customerorders.dto.CustomerOrdersDTO;

public interface CustomerOrdersService {

	public List<CustomerOrdersDTO> getCustomerOrdersList();
	public List<CustomerOrdersDTO> getDataFilter(SearchCondition condition);
}
