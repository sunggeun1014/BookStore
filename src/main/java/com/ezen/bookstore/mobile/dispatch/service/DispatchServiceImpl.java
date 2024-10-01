package com.ezen.bookstore.mobile.dispatch.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.commons.AdminSessionInfo;
import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.mobile.dispatch.dto.RequestDTO;
import com.ezen.bookstore.mobile.dispatch.mapper.DispatchMapper;
import com.ezen.bookstore.mobile.home.mapper.DeliveryMapper;
import com.ezen.bookstore.mobile.warehousing.dto.OrderDetailsDTO;

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
	    // 관리자 ID 설정
	    requestDTO.setManager_id(SessionUtils.getAdminAttribute(AdminSessionInfo.MANAGER_ID));
	    // 트랜잭션 번호를 새로 생성 (NEXTVAL을 통해)
	    Integer logTransactionNum = dispatchMapper.getLastLogTransactionNum();  // 이 시점에 새로운 시퀀스 값을 가져옴
	    requestDTO.setLog_transaction_num(logTransactionNum);  // 생성된 트랜잭션 번호 설정
	    
	    // Inventory_Log에 먼저 삽입 (부모 테이블)
	    dispatchMapper.insertInventoryLog(requestDTO);  // 부모 로그 삽입

	    // request_details 리스트를 순회하면서 처리
	    List<RequestDTO> requestDetails = requestDTO.getRequest_details();  

	    for (RequestDTO orderDetail : requestDetails) {
	        String zoneNum = dispatchMapper.getZoneNum(orderDetail.getBook_isbn());  // zone_num을 가져옴
	        orderDetail.setLog_transaction_num(logTransactionNum);  // 트랜잭션 번호 설정
	        orderDetail.setZone_num(zoneNum);  // zone_num 설정
	        
	        // 인벤토리 수량 차감 처리
	        dispatchMapper.minusInventory(orderDetail);

	        // Inventory_Log_Details 테이블에 로그 기록 (자식 테이블)
	        dispatchMapper.insertInventoryLogDetail(orderDetail);
	        
	    }
	    dispatchMapper.changeRequestStatus(requestDetails.get(0).getRequest_num());
	}



	
}
