package com.ezen.bookstore.mobile.home.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.mobile.home.dto.DeliveryDTO;

@Mapper
public interface DeliveryMapper {
	List<DeliveryDTO> getRequestList();
}
