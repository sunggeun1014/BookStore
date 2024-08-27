package com.ezen.bookstore.commons;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.customerorders.service.CustomerOrdersService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tables")
public class DataTables {
	
	private final CustomerOrdersService cos;
	
    @GetMapping(value = "/customer_orders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> tableData() {
        // DataTables가 요구하는 형식으로 JSON 데이터 구성
        Map<String, Object> responseMap = new HashMap<>();
        List<CustomerOrdersDTO> list = cos.getCustomerOrdersList();
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());

        return responseMap;
    }


}
