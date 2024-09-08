package com.ezen.bookstore.admin.commons;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileManagement {

	public final static String BANNER_PATH = "C:/images/banners/";
	public final static String BOOK_PATH = "C:/images/books/";
	public final static String PROFILE_PATH = "C:/images/profiles/";
	public final static String NOTICE_PATH = "C:/images/notice/";
	
	public final static String BANNER_UPLOAD_NAME = "banner_img_";
	public final static String BOOK_UPLOAD_NAME = "book_img_";
	public final static String PROFILE_UPLOAD_NAME = "profile_img_";
	public final static String NOTICE_UPLOAD_NAME = "notice_img_";
	
	// 이미지 파일 이름 변경 메서드
	public static String generateNewFilename(String originalFilename, String nameToSave) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

		return nameToSave + UUID.randomUUID().toString() + extension;
	}

	// 이미지 파일을 저장하는 메서드
	public static void saveImage(MultipartFile file, String newFilename, String uploadDir) throws IOException {
        Path uploadPath = Paths.get(uploadDir, newFilename); 
    	
    	if (!Files.exists(uploadPath.getParent())) {
    		Files.createDirectories(uploadPath.getParent());
    	}
    	
    	Files.copy(file.getInputStream(), uploadPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
