package com.ezen.bookstore.admin.warehouse.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.admin.warehouse.dto.ZoneNumDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class WarehouseRepository {
	private final SqlSessionTemplate sql;
	
	public List<WarehouseDTO> getWarehouse(){
		return sql.selectList("Warehouse.getAll");
	}
	
	public WarehouseDTO getStockDetails(String invIsbn) {
		return sql.selectOne("Warehouse.getDetail", invIsbn);
	}
	
	public void updateStock(WarehouseDTO warehouseDTO) {
		sql.update("Warehouse.updateStockDetails", warehouseDTO);
	}
	
	public List<ZoneNumDTO> getZoneNum() {
		return sql.selectList("Warehouse.getZoneNum");
	}
	
	public String getZoneNum(String isbn) {
		return sql.selectOne("Warehouse.getOneZoneNum", isbn);
	}
	
	public void invQtyUpdate(String book_isbn, Integer Order_detail_qty) {
		WarehouseDTO dto = new WarehouseDTO();
		
		dto.setInv_isbn(book_isbn);
		dto.setInv_qty(Order_detail_qty);
		sql.update("Warehouse.invQtyUpdate", dto);
	}
	
}
