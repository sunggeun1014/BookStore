package com.ezen.bookstore.user.main.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ezen.bookstore.user.main.dto.UserMainDTO;
import com.ezen.bookstore.user.main.repository.UserMainRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserMainService {
	private final UserMainRepository userMainRepository;

	
	public List<UserMainDTO> getBestBooks() {
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
