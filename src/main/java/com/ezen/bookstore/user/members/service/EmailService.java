package com.ezen.bookstore.user.members.service;

import com.ezen.bookstore.user.members.dto.EmailDTO;

public interface EmailService {
	String sendMail(EmailDTO emailDTO);
	String createCode();
	String setContext(String code);
	boolean verifyCode(String email, String inputCode);
}
