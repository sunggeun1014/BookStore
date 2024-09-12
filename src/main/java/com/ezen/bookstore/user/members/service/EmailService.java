package com.ezen.bookstore.user.members.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.ezen.bookstore.user.members.dto.EmailDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
	
	private final JavaMailSender javaMailSender;
	private final SpringTemplateEngine templateEngine;
	
    private final Map<String, String> verificationStorage = new HashMap<>();

	public String sendMail(EmailDTO emailDTO) {
		String authNum = createCode();
        verificationStorage.put(emailDTO.getTo(), authNum);

		MimeMessage  mimeMessage = javaMailSender.createMimeMessage();
		
		try {
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage , false, "UTF-8");
			mimeMessageHelper.setTo(emailDTO.getTo());
			mimeMessageHelper.setSubject(emailDTO.getSubject());
			mimeMessageHelper.setText(setContext(authNum), true);
			javaMailSender.send(mimeMessage);
			
			log.info("Success");
			
			return authNum;
			
		} catch (MessagingException e) {
			log.info("fail");
			throw new RuntimeException(e);
		}
	}
	
	 public String createCode() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
                case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }
	 
	// thymeleaf를 통한 html 적용
    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("/user/registration/email", context);
    }
    
    public boolean verifyCode(String email, String inputCode) {
        String storedCode = verificationStorage.get(email);  // 저장된 인증번호 가져오기
        return storedCode != null && storedCode.equals(inputCode);
    }
    
    
}