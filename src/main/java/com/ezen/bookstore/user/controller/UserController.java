package com.ezen.bookstore.user.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.main.dto.UserMainDTO;
import com.ezen.bookstore.user.main.service.UserMainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {
	
    private final UserMainService userMainService;
    
	@GetMapping("/login")
	public String login() {
		return "/user/login/login";
	}
	
    @GetMapping("/main")
    public String mainPage(Model model) {
        List<UserMainDTO> banners = userMainService.getBanners();
        List<UserMainDTO> bestBooks = userMainService.getBestBooks();
        List<UserMainDTO> newBooks = userMainService.getNewBooks();
        List<UserMainDTO> topRatedBooks = userMainService.getTopRatedBooks();
        List<UserMainDTO> recommendBooks = userMainService.getRecommendBooks();
                
        model.addAttribute("banners", banners);
        model.addAttribute("bestBooks", bestBooks);
        model.addAttribute("newBooks", newBooks);
        model.addAttribute("topRatedBooks", topRatedBooks);
        model.addAttribute("recommendBooks", recommendBooks);

        return "user/main/main";
    }
}
