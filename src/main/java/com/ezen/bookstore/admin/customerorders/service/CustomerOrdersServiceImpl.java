package com.ezen.bookstore.admin.customerorders.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersListDTO;
import com.ezen.bookstore.admin.customerorders.repository.CustomerOrdersRepository;
import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.admin.warehouse.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrdersServiceImpl implements CustomerOrdersService {
	
	private final CustomerOrdersRepository cor;
	private final WarehouseRepository wr;
	
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

	@Transactional
	@Override
	public void orderStatusUpdate(CustomerOrdersListDTO list, int order_num, String order_selected_status) {
		try {

			
			for(int i = 0; i < list.getOrder_detail_num().size(); i++) {
				System.out.println(list.getOrder_detail_status().get(i));
				System.out.println(order_selected_status);
				if(list.getOrder_detail_status().get(i).equals(order_selected_status)) {
					continue;
				} else if(order_selected_status.equals("06")) {
					// 책 수량 마이너스
					// 입출고 기록하기 (교환)
					wr.invQtyUpdate(list.getBook_isbn().get(i), list.getOrder_detail_qty().get(i));
				} else if(order_selected_status.equals("07")) {
					// 책 수량 플러스
					// 입출고 기록하기 (반품)
				}
				
				cor.orderStatusUpdate(list.getOrder_detail_num().get(i), order_selected_status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	
}
