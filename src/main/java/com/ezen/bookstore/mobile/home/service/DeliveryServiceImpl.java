package com.ezen.bookstore.mobile.home.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.mobile.home.dto.DeliveryDTO;
import com.ezen.bookstore.mobile.home.mapper.DeliveryMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Transactional
@Service
public class DeliveryServiceImpl implements DeliveryService {
	DeliveryMapper deliveryMapper;
	
	@Transactional(readOnly = true)
	@Override
	public List<DeliveryDTO> getRequestList() {
		return deliveryMapper.getRequestList();
	}
	
	@Transactional(readOnly = true)
	@Override
	public List<DeliveryDTO> getRequestDetail(Integer orderNum) {
		return deliveryMapper.getRequestDetail(orderNum);
	}

	@Transactional(readOnly = true)
	@Override
	public List<DeliveryDTO> getOrderStatus() {
		return deliveryMapper.getOrderStatus();
	}
}
