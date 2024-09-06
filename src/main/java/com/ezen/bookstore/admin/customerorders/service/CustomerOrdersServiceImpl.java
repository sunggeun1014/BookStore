package com.ezen.bookstore.admin.customerorders.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.admin.customerorders.repository.CustomerOrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrdersServiceImpl implements CustomerOrdersService {
	
	private final CustomerOrdersRepository cor;
	
	@Override
	public List<CustomerOrdersDTO> getCustomerOrdersList() {
		try {
			return cor.getCustomerOrdersList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<CustomerOrdersDTO> getDataFilter(SearchCondition condition) {
		try {
			return cor.getDataFilterList(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int deliveryRequestSave(List<Integer> order_nums, String manager_id) {
		int result = 0;
		
		try {
			if(cor.deliveryRequestSave(manager_id) > 0) {
				for(int order_num : order_nums) {
					result += cor.deliveryDetailSave(order_num);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public CustomerOrdersDTO getCustomerOrdersDetail(int order_num) {
		try {
			return cor.getCustomerOrdersDetail(order_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<CustomerOrdersDTO> getCustomerOrdersDetailList(int order_num) {
		try {
			return cor.getCustomerOrdersDetailList(order_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void orderStatusUpdate(List<Integer> list, String order_detail_status) {
		try {
			for(int order_detail_num : list) {
				if(order_detail_status.equals("06")) {
					
				} else if(order_detail_status.equals("07")) {
					
				}
				
				cor.orderStatusUpdate(order_detail_num, order_detail_status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
