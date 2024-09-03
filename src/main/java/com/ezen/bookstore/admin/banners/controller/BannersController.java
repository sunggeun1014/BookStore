package com.ezen.bookstore.admin.banners.controller;

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
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.admin.banners.dto.BannersDTO;
import com.ezen.bookstore.admin.banners.service.BannersService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/banners")
@Controller
public class BannersController {
	private final BannersService bannersService;
	
    @GetMapping(value= "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> tableData() {
        List<BannersDTO> tables = bannersService.list();

        Map<String, Object> response = new HashMap<>();
        response.put("data", tables);
        response.put("size", tables.size());

        return response;
    }
	
	@GetMapping("/insert")
	public String insertBannerPage(Model model) {
		
		String templatePath = "/admin/banners/bannerInsert";
        model.addAttribute("template", templatePath); 
		return "/admin/index";
	}
	
	@PostMapping("/insert")
	public String insertBanner(
	    @ModelAttribute BannersDTO bannersDTO,
	    @RequestParam("banner_image") MultipartFile bannerImage, Model model) {
		
	    try {
	        bannersService.insertBanner(bannersDTO, bannerImage);
	        model.addAttribute("success", "배너 등록 성공");
	    } catch (Exception e) {
	        log.error("배너 등록 실패", e);
	        model.addAttribute("error", "배너 등록 실패");
	    }

	    return "redirect:/admin/index?path=admin/banners/banners";
	}


    @PostMapping("/details")
    public String showBannerDetail(@RequestParam("banner_num") Integer bannerNum, Model model) {
    	BannersDTO banners = bannersService.detailList(bannerNum);
    	
    	model.addAttribute("banners", banners);

		String templatePath = "/admin/banners/bannerDetail";
        model.addAttribute("template", templatePath); 
        
        return "/admin/index";
        
    }
    
    @PostMapping("/update")
    public String updateBanner(@ModelAttribute BannersDTO bannersDTO,
    	    @RequestParam("banner_image") MultipartFile bannerImage, 
    	    Model model) {
    	
    	try {
    		bannersService.updateBanner(bannersDTO, bannerImage);
			model.addAttribute("success", "배너 수정 성공");
		} catch (Exception e) {
            log.error("배너 수정 실패", e);
            model.addAttribute("error", "배너 수정 실패: " + e.getMessage());
		}
    	
    	return "redirect:/admin/index?path=admin/banners/banners";
    }
    

    @PostMapping("/delete")
    public ResponseEntity<?> deleteBanners(@RequestBody List<Integer> bannerNums) {
        try {
            bannersService.deleteBanners(bannerNums);
            return ResponseEntity.ok("삭제가 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("삭제 중 오류가 발생했습니다.");
        }
    }
}
