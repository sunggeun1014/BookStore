package com.ezen.bookstore.mobile.dispatch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.mobile.dispatch.dto.RequestDTO;
import com.ezen.bookstore.mobile.dispatch.service.DispatchService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/mobile/admin")
public class DispatchRestController {
	
	DispatchService dispatchService;
	
	@PostMapping("/get-inventoryList")
    public Map<String, Object> getInventoryList(@RequestParam("zoneNum") String zoneNum) {
        Map<String, Object> response = new HashMap<>();
        
        List<WarehouseDTO> dispatchList = dispatchService.getDispatchList(zoneNum);

        response.put("status", "success");
        response.put("data", dispatchList);

        return response; 
    }
	
	@PostMapping("get-requestDetails")
	public ResponseEntity<?> getOrderDetails(@RequestParam("requestNum") Integer requestNum) {
		try {
            List<RequestDTO> requestDetails = dispatchService.getRequestDetail(requestNum);

            if (requestDetails.isEmpty()) {
                return ResponseEntity.status(404).body("요청 정보를 찾을 수 없습니다.");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", requestDetails);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러가 발생했습니다.");
        }
	}
	
	@PostMapping("/stockout-inventory")
	public ResponseEntity<?> processInventory(@RequestBody RequestDTO requestDTO) {
	    try {
	        // 주문 항목 리스트 처리
//	        List<RequestDTO> requestDetails = requestDTO.getRequest_details();
	        
	        // 주문 항목을 처리하는 서비스 로직 호출
	        dispatchService.minusInventoryLog(requestDTO);
	       
	        
	        Map<String, Object> response = new HashMap<>();
	        response.put("status", "success");
	        
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inventory processing failed");
	    }
	}


	
}
