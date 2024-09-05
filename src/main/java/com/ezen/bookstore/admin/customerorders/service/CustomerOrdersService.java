package com.ezen.bookstore.admin.customerorders.service;

import java.util.List;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;

public interface CustomerOrdersService {

	public List<CustomerOrdersDTO> getCustomerOrdersList();
	public List<CustomerOrdersDTO> getDataFilter(SearchCondition condition);
	public int deliveryRequestSave(List<Integer> order_nums, String manager_id);
	public CustomerOrdersDTO getCustomerOrdersDetail(int order_num);
	public List<CustomerOrdersDTO> getCustomerOrdersDetailList(int order_num);
	public void orderStatusUpdate(List<Integer> list, String order_detail_status);
}
