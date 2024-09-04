package com.ezen.bookstore.admin.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

	    String fullEmail = managersDTO.getManager_email();
	    String localPart = fullEmail != null ? fullEmail.split("@")[0] : "";
	
	    managersDTO.setManager_email(localPart);
	    
	    String fullPhone = managersDTO.getManager_phoneNo();
	    String countryNum = "";
	    String userPart1 = "";
	    String userPart2 = "";

	    if (fullPhone != null && !fullPhone.isEmpty()) {
	        String[] phoneParts = fullPhone.split("-");
	        if (phoneParts.length == 3) {
	            countryNum = phoneParts[0];
	            userPart1 = phoneParts[1];
	            userPart2 = phoneParts[2];
	        }
	    }
	    
	    
	    model.addAttribute("managers", managersDTO);
	    model.addAttribute("countryNum", countryNum);
	    model.addAttribute("userPart1", userPart1);
	    model.addAttribute("userPart2", userPart2);
	    String templatePath = "/admin/myinfo/myinfo";
	    
	    model.addAttribute("template", templatePath);
	    
	    return "admin/index";
	}

	
	@PostMapping("/myinfo/update")
    public String updateMyInfo(@ModelAttribute("managers") ManagersDTO managersDTO,
                               @RequestParam MultipartFile profileImage,
                               HttpSession session,
                               Model model) {

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
                String projectDir = System.getProperty("user.dir");  // 프로젝트의 현재 작업 디렉토리 경로
                String uploadDir = projectDir + "/src/main/resources/static/admin/common/img/profile/"; // 파일 저장 폴더 경로
                Path uploadPath = Paths.get(uploadDir, newFileName);

                // 폴더가 없으면 생성
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // 파일을 지정된 경로에 저장
                profileImage.transferTo(uploadPath.toFile());

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
