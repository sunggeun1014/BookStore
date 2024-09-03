package com.ezen.bookstore.admin.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.managers.service.MgrService;
import com.ezen.bookstore.admin.security.service.CustomUserDetails;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
@Controller
public class AdminController {
	
	private final MgrService mgrService;
	
	@GetMapping("/login")
    public String login(Authentication authentication) {

        return "/admin/login/login";
    }
	
	@GetMapping("/index")
    public String index(HttpSession session, Model model, String path) {
		
		model.addAttribute("template", path);
		
		log.info("{}", session.getAttribute("username"));
		
		if(session.getAttribute("username") != null) {
			 return "/admin/index";
		}
		
        // 현재 인증된 사용자 정보 가져오기
		// Authentication : 현재 사용자의 정보를 가져오기 위해 사용하는 객체
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof CustomUserDetails) {
        	// 사용자의 정보를 담고있는 객체이다
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            
            String managerId = userDetails.getUsername();
            String username = userDetails.getManagerName();
            
            // 부서명을 설정
            String departmentName;
            switch (userDetails.getManagerDept()) {
                case "01":
                    departmentName = "물류팀";
                    break;
                case "02":
                    departmentName = "운영팀";
                    break;
                default:
                    departmentName = "기타";
                    break;
            }
            
            session.setAttribute("username", username);
            session.setAttribute("authority", departmentName);
            session.setAttribute("managerId", managerId);
        }

        return "/admin/index";
       
    }
	
	@GetMapping("/myinfo")
	public String getMyInfo(HttpSession session, Model model) {
	    
	    String managerId = (String) session.getAttribute("managerId");
	    log.info(managerId);
	    
	    ManagersDTO managersDTO = mgrService.detailList(managerId);
	    log.info("ManagersDTO: " + managersDTO); // DTO 객체를 로그로 출력
	    
	    if (managersDTO != null) {
	    	model.addAttribute("managers", managersDTO);
	    }
	    
	    String templatePath = "/admin/myinfo/myinfo";
	    
	    model.addAttribute("template", templatePath);
	    
	    return "admin/index";
	}

	
	@PostMapping("/myinfo/update")
	public String updateMyInfo(@ModelAttribute("managers") ManagersDTO managersDTO,
	                           @RequestParam MultipartFile profileImage,
	                           HttpSession session,
	                           Model model) {

		// 이미지가 비어있지 않은 경우에만 처리
	    if (!profileImage.isEmpty()) {
	        try {
	            // 원본 파일 이름과 확장자 추출
	            String originalFilename = profileImage.getOriginalFilename();
	            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

	            // 현재 시간으로 새로운 파일 이름 생성
	            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	            String formattedTime = LocalDateTime.now().format(formatter);
	            String newFileName = "profile_img_" + formattedTime + fileExtension;

	            // 프로젝트의 정적 리소스 디렉토리에 이미지 저장 경로 설정
	            // 절대 경로로 변환하여 스프링 부트 실행 경로 기준으로 설정
	            String uploadDir = new File("src/main/resources/static/common/img/profile/").getAbsolutePath();
	            File uploadDirFile = new File(uploadDir);
	            if (!uploadDirFile.exists()) {
	                uploadDirFile.mkdirs(); // 디렉토리가 없으면 생성
	            }

	            String uploadPath = uploadDir + File.separator + newFileName;

	            // 파일을 지정된 경로에 저장
	            profileImage.transferTo(new File(uploadPath));

	            // DTO에 파일 정보 설정
	            managersDTO.setManager_profile_original(originalFilename);
	            managersDTO.setManager_profile_changed(newFileName);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return "admin/myinfo"; // 에러 발생 시 다시 마이페이지로 이동
	        }
	    }


	    // 서비스 호출하여 데이터베이스 업데이트
	    mgrService.updateManager(managersDTO);

	    return "admin/index";  // 업데이트 후 마이페이지로 리다이렉트
	}
    
}
