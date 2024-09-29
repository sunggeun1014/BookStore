package com.ezen.bookstore.mobile.warehousing.controller;

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
import com.ezen.bookstore.mobile.warehousing.service.WareHousingService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/mobile/admin")
@RestController
public class WareHousingRestController {
	
	WareHousingService wareHousingService;
	
	@PostMapping("/get-orderDetails")
	public ResponseEntity<?> getOrderDetails(@RequestParam("orderNum") Integer orderNum) {
		try {
            List<SupplierOrdersDTO> orderDetails = wareHousingService.getOrderDetails(orderNum);

            if (orderDetails.isEmpty()) {
                return ResponseEntity.status(404).body("발주 정보를 찾을 수 없습니다.");
            }

            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            response.put("data", orderDetails);

            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            return ResponseEntity.status(500).body("서버 에러가 발생했습니다.");
        }
	}
	
	
	@PostMapping("/process-inventory")
    public ResponseEntity<?> processInventory(@RequestBody SupplierOrdersDTO orderDetails) {
        try {
            wareHousingService.saveInventoryLog(orderDetails);
            
            Map<String, Object> response = new HashMap<>();
            response.put("status", "success");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	e.printStackTrace();
        	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Inventory processing failed");
        }
    }
}
