package com.ezen.bookstore.mobile.dispatch.service;

import java.util.List;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.mobile.dispatch.dto.RequestDTO;

public interface DispatchService {
	List<WarehouseDTO> getDispatchList(String zoneNum);
	void minusInventoryLog(RequestDTO requestDTO);
	List<RequestDTO> getRequestDetail(Integer orderNum);

}
