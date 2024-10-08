package com.ezen.bookstore.mobile.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.mobile.home.dto.DeliveryDTO;

@Mapper
public interface DeliveryMapper {
	List<DeliveryDTO> getRequestList();
	List<DeliveryDTO> getRequestDetail(@Param("request_num") Integer requestNum);
	List<DeliveryDTO> getOrderStatus();
}
