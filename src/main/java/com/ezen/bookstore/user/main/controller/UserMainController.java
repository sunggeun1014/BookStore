package com.ezen.bookstore.user.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.user.main.service.UserMainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user/main")
@Controller
public class UserMainController {
	
	private final UserMainService userMainService;

	
    
    @GetMapping("/user/main")
    public String mainPage(Model model) {
        List<ProductsDTO> books = userMainService.getNewBooks(); // 책 데이터 가져오기
        model.addAttribute("books", books);
        return "main"; // Thymeleaf 템플릿 이름
    }
    
    @GetMapping(value = "/new-books/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getNewBooks() {
        List<ProductsDTO> books = userMainService.getNewBooks();
        Map<String, Object> response = new HashMap<>();
        response.put("data", books);
        return response;
    }

}