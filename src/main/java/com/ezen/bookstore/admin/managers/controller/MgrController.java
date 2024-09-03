package com.ezen.bookstore.admin.managers.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.managers.service.MgrService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin/managers")
public class MgrController {
	private final MgrService mgrService;
	
	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> tableData(){
		List<ManagersDTO> tables = mgrService.list();
		
		Map<String, Object> response = new HashMap<>();
		response.put("data", tables);
        response.put("size", tables.size());


		return response;
	}
	
	@PostMapping("/details")
	public String showMemberDetails(@RequestParam("manager_id") String managerID, Model model) {
	    ManagersDTO managerDetails = mgrService.detailList(managerID);

	    String[] emailParts = managerDetails.getManager_email().split("@");
	    String emailUser = emailParts[0];
	    String emailDomain = emailParts[1];

	    String[] phoneNumber = managerDetails.getManager_phoneNo().split("-");
	    String countryNum = phoneNumber[0];
	    String userPart1 = phoneNumber[1];
	    String userPart2 = phoneNumber[2];

	    model.addAttribute("manager_details", managerDetails);
	    model.addAttribute("emailUser", emailUser);
	    model.addAttribute("emailDomain", emailDomain);
	    model.addAttribute("countryNum", countryNum);
	    model.addAttribute("userPart1", userPart1);
	    model.addAttribute("userPart2", userPart2);
	    
	    String templatePath = "/admin/managers/managerDetails";
        model.addAttribute("template", templatePath);  // 경로를 template로 전달

	    return "/admin/index";
	}
	
	@PostMapping("/update/dept")
    public ResponseEntity<String> deleteReviews(@RequestBody Map<String, Object> payload) {
        try {
            // 리뷰 삭제를 서비스에 위임
        	List<String> managerIds = (List<String>) payload.get("managerId");
            String dept = (String) payload.get("managerDept");
            
            for (String managerId : managerIds) {
                mgrService.changeDept(managerId, dept);
            }
            
            return ResponseEntity.ok("변경이 완료되었습니다.");
        } catch (Exception e) {
            e.printStackTrace(); // 서버 로그에 오류 출력
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("변경 중 오류가 발생했습니다.");
        }
    }
	
	@PostMapping("/checkId")
	@ResponseBody
	public ResponseEntity<Map<String, Boolean>> checkManagerId(@RequestBody Map<String, String> payload) {
	    String managerId = payload.get("manager_id");
	    boolean isAvailable = mgrService.isManagerIdAvailable(managerId);
	    
	    Map<String, Boolean> response = new HashMap<>();
	    response.put("isAvailable", isAvailable);
	    
	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/join")
	public String joinProccess(@ModelAttribute ManagersDTO managersDTO, 
					            @RequestParam("emailUser") String emailUser, 
					            @RequestParam("emailDomain") String emailDomain,
					            @RequestParam("countryNum") String countryNum,
					            @RequestParam("userPart1") String userPart1,
					            @RequestParam("userPart2") String userPart2,
					            Model model
								) {
		
		if (!mgrService.isManagerIdAvailable(managersDTO.getManager_id())) {
			
	        model.addAttribute("errorCode", 1);
	        
	        model.addAttribute("managersDTO", managersDTO);
	        model.addAttribute("emailUser", emailUser);
	        model.addAttribute("emailDomain", emailDomain);
	        model.addAttribute("countryNum", countryNum);
	        model.addAttribute("userPart1", userPart1);
	        model.addAttribute("userPart2", userPart2);
	        
	        String templatePath = "/admin/managers/managerReg";
	        model.addAttribute("template", templatePath);  // 경로를 template로 전달
	        
	        return "/admin/index";
	    }
		
		String manager_email = emailUser +"@" + emailDomain;
		String manager_phoneNo = countryNum + "-" + userPart1 + "-" + userPart2;
	    Timestamp now = Timestamp.valueOf(LocalDateTime.now());
	    String originName = "1111";
	    String changed = "1111";
		
		managersDTO.setManager_email(manager_email);
		managersDTO.setManager_phoneNo(manager_phoneNo);
		managersDTO.setManager_join_date(now);
		managersDTO.setManager_profile_changed(changed);
		managersDTO.setManager_profile_original(originName);
		
		
		mgrService.joinProcess(managersDTO);
		
		return "redirect:/admin/index?path=admin/managers/managers";
	}
	
	
	

}
