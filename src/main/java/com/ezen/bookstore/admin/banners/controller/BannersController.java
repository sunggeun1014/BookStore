package com.ezen.bookstore.admin.banners.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.ezen.bookstore.commons.FileManagement;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/banners")
@Controller
public class BannersController {
	private final BannersService bannersService;
	
	
	@GetMapping("/banners")
	public String bannersList() {
		return "admin/banners/banners";
	}

	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
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
		return "admin/banners/bannerInsert";
	}

	@PostMapping("/insert")
	public String insertBanner(@ModelAttribute BannersDTO bannersDTO,
			@RequestParam("banner_image") MultipartFile bannerImage, Model model) {
		try {
			// 이미지 업로드 처리
			if (!bannerImage.isEmpty()) {
				String originalFileName = bannerImage.getOriginalFilename();
				String newFileName = FileManagement.generateNewFilename(originalFileName, FileManagement.BANNER_UPLOAD_NAME);
            	
            	FileManagement.saveImage(bannerImage, newFileName, FileManagement.BANNER_PATH);
            	
            	bannersDTO.setBanner_original(originalFileName);
				bannersDTO.setBanner_changed(newFileName);
			}
				
			bannersService.insertBanner(bannersDTO);
			model.addAttribute("success", "배너 등록 성공");
		} catch (IOException e) {
			e.printStackTrace();
		}
	    return "redirect:/admin/banners/banners";
	}

	@PostMapping("/details")
	public String showBannerDetail(@RequestParam("banner_num") Integer bannerNum, Model model) {
		BannersDTO banners = bannersService.detailList(bannerNum);
        
		model.addAttribute("banners", banners);

        return "admin/banners/bannerDetail";
        
    }

	@PostMapping("/update")
	public String updateBanner(@ModelAttribute BannersDTO bannersDTO,
			@RequestParam(value="banner_image", required = false) MultipartFile bannerImage, Model model) {
		
		try {
			// 업로드한 이미지가 있는 경우
			if (!bannerImage.isEmpty()) {
				String originalFileName = bannerImage.getOriginalFilename();
				String newFileName = FileManagement.generateNewFilename(originalFileName, FileManagement.BANNER_UPLOAD_NAME);
				
            	FileManagement.saveImage(bannerImage, newFileName, FileManagement.BANNER_PATH);
            	
				bannersDTO.setBanner_original(originalFileName);
				bannersDTO.setBanner_changed(newFileName);
			} else {
				// 이미지가 업로드되지 않은 경우
	            BannersDTO existBanner = bannersService.detailList(bannersDTO.getBanner_num());
	            bannersDTO.setBanner_original(existBanner.getBanner_original());
	            bannersDTO.setBanner_changed(existBanner.getBanner_changed());
			}
			bannersService.updateBanner(bannersDTO);
			model.addAttribute("success", "배너 업데이트 성공");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/admin/banners/banners";
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
