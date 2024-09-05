package com.ezen.bookstore.admin.home.controller;

import com.ezen.bookstore.admin.home.service.HomeService;
import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;
import com.ezen.bookstore.admin.inquiries.service.InquiriesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/home")
@Controller
public class HomeController {
    private final HomeService homeService;
    private final InquiriesService iqs;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("template", "admin/home/home");
        return "admin/index";
    }

    @GetMapping(value = "/inquiries/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getInquiriesData() {
        List<InquiriesDTO> tables = iqs.getList();

        // DataTables가 요구하는 형식으로 JSON 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("data", tables);
        response.put("size", tables.size());

        return response;
    }

    @GetMapping(value = "/stocks/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getStocksData() {
        List<InquiriesDTO> tables = iqs.getList();

        // DataTables가 요구하는 형식으로 JSON 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("data", tables);
        response.put("size", tables.size());

        return response;
    }
}
