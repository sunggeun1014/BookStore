package com.ezen.bookstore.admin.warehouse.service;

import java.util.List;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.admin.warehouse.dto.ZoneNumDTO;

public interface WarehouseService {
	
	List<WarehouseDTO> list();
	WarehouseDTO detailList(String invIsbn);
	void updateStockDetails(WarehouseDTO warehouseDTO);
	List<ZoneNumDTO> zoneNumList();
}
