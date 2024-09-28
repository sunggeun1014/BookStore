package com.ezen.bookstore.mobile.dispatch.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;

@Mapper
public interface DispatchMapper {
	List<WarehouseDTO> getDispatchList(@RequestParam("zone_num") String zoneNum);
}
