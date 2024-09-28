package com.ezen.bookstore.mobile.dispatch.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.mobile.dispatch.mapper.DispatchMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
@Service
public class DispatchServiceImpl implements DispatchService {
	
	DispatchMapper dispatchMapper;
	
	@Transactional(readOnly = true)
	@Override
	public List<WarehouseDTO> getDispatchList(String zoneNum) {
		return dispatchMapper.getDispatchList(zoneNum);		
	}

}
