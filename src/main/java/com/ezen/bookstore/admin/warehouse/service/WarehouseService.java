package com.ezen.bookstore.admin.warehouse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.admin.warehouse.dto.ZoneNumDTO;
import com.ezen.bookstore.admin.warehouse.repository.WarehouseRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseService {
	
	private final WarehouseRepository warehouseRepository;
	
	@Transactional(readOnly = true)
    public List<WarehouseDTO> list() {
		
        return warehouseRepository.getWarehouse();
    }
	
    @Transactional(readOnly = true)
    public WarehouseDTO detailList(String invIsbn) {
    	return warehouseRepository.getStockDetails(invIsbn);
    }
    
    @Transactional(readOnly = true)
    public void updateStockDetails(WarehouseDTO warehouseDTO) {
    	warehouseRepository.updateStock(warehouseDTO);
    }
    
	@Transactional(readOnly = true)
	public List<ZoneNumDTO> zoneNumList() {
		return warehouseRepository.getZoneNum();
	}
}
