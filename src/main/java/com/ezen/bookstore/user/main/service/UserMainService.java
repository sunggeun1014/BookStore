package com.ezen.bookstore.user.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.main.dto.UserMainDTO;
import com.ezen.bookstore.user.main.repository.UserMainRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserMainService {
	private final UserMainRepository userMainRepository;

	public List<UserMainDTO> getBanners() {
		log.info("banners: {}", userMainRepository.getBanners());
		return userMainRepository.getBanners();
	}
	
	public List<UserMainDTO> getBestBooks() {
		log.info("bestbooks: {}", userMainRepository.getBestBooks());
		return userMainRepository.getBestBooks();
	}

    public List<UserMainDTO> getNewBooks() {
        return userMainRepository.getNewBooks();
    }
    
    public List<UserMainDTO> getTopRatedBooks() {
    	return userMainRepository.getTopRatedBooks();
    }
    
    public List<UserMainDTO> getRecommendBooks() {
    	return userMainRepository.getRecommendBooks();
    }

    
}
