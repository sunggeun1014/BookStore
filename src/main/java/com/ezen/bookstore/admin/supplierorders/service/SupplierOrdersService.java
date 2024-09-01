package com.ezen.bookstore.admin.supplierorders.service;

import java.util.List;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;

public interface SupplierOrdersService {
	public List<SupplierOrdersDTO> getSupplierOrdersList();
	public List<SupplierOrdersDTO> getDataFilter(SearchCondition condition);
}
