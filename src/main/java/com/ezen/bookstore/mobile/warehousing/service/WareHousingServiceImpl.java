package com.ezen.bookstore.mobile.warehousing.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.commons.AdminSessionInfo;
import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.mobile.warehousing.dto.OrderDetailsDTO;
import com.ezen.bookstore.mobile.warehousing.mapper.WareHousingMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class WareHousingServiceImpl implements WareHousingService {
	
	WareHousingMapper wareHousingMapper;
	
	@Transactional(readOnly = true)
	@Override
	public List<SupplierOrdersDTO> getOrderDetails(Integer orderNum) {
		return wareHousingMapper.getOrderDetails(orderNum);
	}
	
	@Override
	public void saveInventoryLog(SupplierOrdersDTO supplierOrdersDTO) {
		
		supplierOrdersDTO.setManager_id(SessionUtils.getAdminAttribute(AdminSessionInfo.MANAGER_ID));
		wareHousingMapper.insertInventoryLog(supplierOrdersDTO);
		
		Integer logTransactionNum = wareHousingMapper.getLastLogTransactionNum();

		
		List<OrderDetailsDTO> list = supplierOrdersDTO.getOrder_details();
		for (OrderDetailsDTO orderDetail : list) {
			 orderDetail.setLog_transaction_num(logTransactionNum);
	         orderDetail.setZone_num(supplierOrdersDTO.getZone_num());
	         
	         wareHousingMapper.updateReceivedQty(orderDetail.getOrder_detail_num(), orderDetail.getOrder_detail_qty());
	         
	         wareHousingMapper.insertInventoryLogDetail(orderDetail);
		}
	}
}
