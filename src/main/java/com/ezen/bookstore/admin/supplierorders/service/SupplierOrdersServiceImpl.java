package com.ezen.bookstore.admin.supplierorders.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.admin.supplierorders.repository.SupplierOrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierOrdersServiceImpl implements SupplierOrdersService {
	
	private final SupplierOrdersRepository sor;
	
	@Override
	public List<SupplierOrdersDTO> getSupplierOrdersList() {
		try {
			return sor.getSupplierOrdersList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<SupplierOrdersDTO> getDataFilter(SearchCondition condition) {
		try {
			return sor.getDataFilter(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<SupplierOrdersDTO> getSupplierOrdersDetailList(Integer order_num) {
		try {
			return sor.getSupplierOrdersDetailList(order_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
		return null;
	}

	@Override
	public SupplierOrdersDTO getSupplierOrdersDetail(Integer order_num) {
		try {
			return sor.getSupplierOrdersDetail(order_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void orderConfirmInsert(List<SupplierOrdersDTO> list, String manager_id) {
		try {
			sor.orderConfirmInsert(list, manager_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
