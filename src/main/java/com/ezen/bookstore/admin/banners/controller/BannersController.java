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
	
	@GetMapping("/banners")
	public String list() {
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
			Map<String, String> fileInfo = imageUpload(bannerImage);
			if (fileInfo != null) {
				bannersDTO.setBanner_original(fileInfo.get("original"));
				bannersDTO.setBanner_changed(fileInfo.get("changed"));
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
			if (bannerImage != null && !bannerImage.isEmpty()) {
				Map<String, String> fileInfo = imageUpload(bannerImage);
				bannersDTO.setBanner_original(fileInfo.get("original"));
				bannersDTO.setBanner_changed(fileInfo.get("changed"));
			} else {
				// 이미지가 업로드되지 않은 경우
	            BannersDTO existBanner = bannersService.detailList(bannersDTO.getBanner_num());
	            bannersDTO.setBanner_original(existBanner.getBanner_original());
	            bannersDTO.setBanner_changed(existBanner.getBanner_changed());
			}
			bannersService.updateBanner(bannersDTO);
			model.addAttribute("success", "배너 업데이트 성공");
		} catch (IOException e) {
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
	

    @PostMapping("/updateExpiredBanners")
    public ResponseEntity<?> updateExpiredBanners() {
        try {
            Date today = new Date();
            List<BannersDTO> expiredBanners = bannersService.findExpiredBanners(today);

            for (BannersDTO banner : expiredBanners) {
                banner.setBanner_visible("비노출");
                bannersService.updateBanner(banner);
            }
            return ResponseEntity.ok("만료된 배너 상태를 '비노출'로 업데이트했습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("업데이트 중 오류가 발생했습니다.");
        }
    }

	private Map<String, String> imageUpload(MultipartFile bannerImage) throws IOException {
		if (bannerImage == null || bannerImage.isEmpty()) {
			return null;
		}

		// 원본 파일 이름과 확장자 추출
		String originalFilename = bannerImage.getOriginalFilename();
		String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

		// 현재 시간으로 새로운 파일 이름 생성
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedTime = LocalDateTime.now().format(formatter);
		String newFileName = "banner_img_" + formattedTime + fileExtension;

		// 프로젝트의 정적 리소스 디렉토리에 이미지 저장 경로 설정
		String projectDir = System.getProperty("user.dir"); // 프로젝트의 현재 작업 디렉토리 경로
		String uploadDir = projectDir + "/src/main/resources/static/admin/common/img/banners/"; // 파일 저장 폴더 경로
		Path uploadPath = Paths.get(uploadDir, newFileName);

		// 폴더가 없으면 생성
		File uploadDirFile = new File(uploadDir);
		if (!uploadDirFile.exists()) {
			uploadDirFile.mkdirs();
		}

		// 파일을 지정된 경로에 저장
		bannerImage.transferTo(uploadPath.toFile());

		// 새로운 파일 이름과 원본 파일 이름 반환
		Map<String, String> fileInfo = new HashMap<>();
		fileInfo.put("original", originalFilename);
		fileInfo.put("changed", newFileName);
		return fileInfo;
	}

}
