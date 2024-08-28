package com.ezen.bookstore.customerorders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.commons.SearchCondition;
import com.ezen.bookstore.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.customerorders.service.CustomerOrdersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rest")
public class CustomerOrdersRestController {
	
	private final CustomerOrdersService cos;
	
    @GetMapping(value = "/customerOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTableData() {
        // DataTables가 요구하는 형식으로 JSON 데이터 구성
        Map<String, Object> responseMap = new HashMap<>();
        List<CustomerOrdersDTO> list = cos.getCustomerOrdersList();
        
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());

        return responseMap;
    }
    
    @GetMapping(value = "dataFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDataFilter(SearchCondition condition) {
    	Map<String, Object> responseMap = new HashMap<>();
        List<CustomerOrdersDTO> list = cos.getDataFilter(condition);
        
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());
    	
    	return responseMap;
    }
    
}
