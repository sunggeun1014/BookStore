package com.ezen.bookstore.mobile.dispatch.service;

import java.util.List;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;

public interface DispatchService {
	List<WarehouseDTO> getDispatchList(String zoneNum);
}
