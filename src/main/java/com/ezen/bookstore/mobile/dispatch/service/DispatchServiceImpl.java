package com.ezen.bookstore.mobile.dispatch.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.commons.AdminSessionInfo;
import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.mobile.dispatch.dto.RequestDTO;
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
	
	@Transactional(readOnly = true)
	@Override
	public List<RequestDTO> getRequestDetail(Integer requestNum) {
		return dispatchMapper.getRequestDeatil(requestNum);
	}
	
	@Override
	@Transactional
	public void minusInventoryLog(RequestDTO requestDTO) {

		requestDTO.setManager_id(SessionUtils.getAdminAttribute(AdminSessionInfo.MANAGER_ID));

		Integer logTransactionNum = dispatchMapper.getLastLogTransactionNum();  
	    requestDTO.setLog_transaction_num(logTransactionNum);
	    
	    dispatchMapper.insertInventoryLog(requestDTO); 

	    List<RequestDTO> requestDetails = requestDTO.getRequest_details();  

	    for (RequestDTO orderDetail : requestDetails) {
	        String zoneNum = dispatchMapper.getZoneNum(orderDetail.getBook_isbn()); 
	        orderDetail.setLog_transaction_num(logTransactionNum); 
	        orderDetail.setZone_num(zoneNum); 
	        
	        dispatchMapper.minusInventory(orderDetail);

	        dispatchMapper.insertInventoryLogDetail(orderDetail);
	        
	    }
	    dispatchMapper.changeOrederStatus(requestDetails.get(0).getRequest_num());
	    dispatchMapper.changeRequestStatus(requestDetails.get(0).getRequest_num());
	}



	
}
