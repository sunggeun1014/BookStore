package com.ezen.bookstore.mobile.dispatch.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
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
        
        // Service를 통해 DispatchList 조회
        List<WarehouseDTO> dispatchList = dispatchService.getDispatchList(zoneNum);

        // 조회된 데이터를 Map에 담음
        response.put("status", "success");
        response.put("data", dispatchList);

        return response; // JSON 형식으로 반환됨
    }
}
