package com.ezen.bookstore.user.main.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.main.dto.UserMainDTO;
import com.ezen.bookstore.user.main.service.UserMainService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RequestMapping("/user/main")
@Controller
@Slf4j
public class UserMainController {
    
    private final UserMainService userMainService;

    @GetMapping("/")
    public String mainPage(Model model) {
        List<UserMainDTO> banners = userMainService.getBanners();
        List<UserMainDTO> bestBooks = userMainService.getBestBooks();
        List<UserMainDTO> newBooks = userMainService.getNewBooks();
        List<UserMainDTO> topRatedBooks = userMainService.getTopRatedBooks();
        List<UserMainDTO> recommendBooks = userMainService.getRecommendBooks();
        
        // 데이터가 제대로 로드되었는지 로그로 확인
        log.info("Banners: {}", banners);
        log.info("Best Books: {}", bestBooks);
        log.info("New Books: {}", newBooks);
        log.info("Top Rated Books: {}", topRatedBooks);
        log.info("Recommend Books: {}", recommendBooks);
        
        // 모델에 데이터를 추가
        model.addAttribute("banners", banners);
        model.addAttribute("bestBooks", bestBooks);
        model.addAttribute("newBooks", newBooks);
        model.addAttribute("topRatedBooks", topRatedBooks);
        model.addAttribute("recommendBooks", recommendBooks);

        return "user/main/main"; // Thymeleaf 템플릿 파일 경로
    }
}
