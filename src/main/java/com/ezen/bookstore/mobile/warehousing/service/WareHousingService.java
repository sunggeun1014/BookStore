package com.ezen.bookstore.mobile.warehousing.service;

import java.util.List;

import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;

public interface WareHousingService {
	
	List<SupplierOrdersDTO> getOrderDetails(Integer orderNum);
	
	void saveInventoryLog(SupplierOrdersDTO supplierOrdersDTO);
}
