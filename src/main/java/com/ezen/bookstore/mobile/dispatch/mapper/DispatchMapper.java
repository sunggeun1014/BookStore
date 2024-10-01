package com.ezen.bookstore.mobile.dispatch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.mobile.dispatch.dto.RequestDTO;
import com.ezen.bookstore.mobile.warehousing.dto.OrderDetailsDTO;

@Mapper
public interface DispatchMapper {
	List<WarehouseDTO> getDispatchList(@RequestParam("zone_num") String zoneNum);
    List<WarehouseDTO> geotItemsByIsbns(List<String> isbns);
    List<RequestDTO> getRequestDeatil(@Param("request_num") Integer requestNum);
    void minusInventory(RequestDTO requestDTO);
	Integer getLastLogTransactionNum();
	String getZoneNum (@Param("isbn") String isbn);
	void insertInventoryLog(RequestDTO requestDTO);
	void insertInventoryLogDetail(RequestDTO requestDTO);
	void changeOrederStatus(Integer requestNum);
	void changeRequestStatus(Integer requestNum);
}
