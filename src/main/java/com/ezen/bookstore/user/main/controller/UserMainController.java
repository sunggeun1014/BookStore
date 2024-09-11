package com.ezen.bookstore.user.main.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.user.main.dto.UserMainDTO;
import com.ezen.bookstore.user.main.service.UserMainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/user/main")
@Controller
public class UserMainController {
	
	private final UserMainService userMainService;

    @GetMapping(value = "/best-books/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getBestBooks() {
        List<UserMainDTO> books = userMainService.getBestBooks();
        Map<String, Object> response = new HashMap<>();
        response.put("data", books);
        return response;
    }
    
    @GetMapping(value = "/new-books/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getNewBooks() {
    	List<UserMainDTO> books = userMainService.getNewBooks();
    	Map<String, Object> response = new HashMap<>();
    	response.put("data", books);
    	return response;
    }
    
    @GetMapping(value = "/top-rated-books/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getTopRatedBooks() {
    	List<UserMainDTO> books = userMainService.getTopRatedBooks();
    	Map<String, Object> response = new HashMap<>();
    	response.put("data", books);
    	return response;
    }
    
    @GetMapping(value = "/recommend-books/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getRecommendBooks() {
        List<UserMainDTO> books = userMainService.getRecommendBooks();
        Map<String, Object> response = new HashMap<>();
        response.put("data", books);
        return response;
    }

}
