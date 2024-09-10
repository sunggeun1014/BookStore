package com.ezen.bookstore.user.members.service;

import jakarta.servlet.http.HttpSession;

public interface UserMyPageService {
	boolean findPwById(HttpSession session, String password);
}
