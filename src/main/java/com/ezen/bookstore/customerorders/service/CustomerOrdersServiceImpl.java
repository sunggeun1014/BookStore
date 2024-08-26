package com.ezen.bookstore.customerorders.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.customerorders.repository.CustomerOrdersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrdersServiceImpl implements CustomerOrdersService {
	
	private final CustomerOrdersRepository cor;
	
	@Override
	public List<CustomerOrdersDTO> getCustomerOrdersList() {
		return cor.getCustomerOrdersList();
	}

	
	
}
