package com.ezen.bookstore.admin.banners.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.admin.banners.dto.BannersDTO;
import com.ezen.bookstore.admin.banners.repository.BannersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BannersService {

	private final BannersRepository bannersRepository;
	
	public List<BannersDTO> list(){
		return bannersRepository.getAll();
	}
	
	public BannersDTO detailList(Integer bannerNum) {
		return bannersRepository.getBannerDetail(bannerNum);
	}
	
	@Transactional
	public void updateBanner(BannersDTO bannersDTO, MultipartFile bannerImage) {
		try {
			imageUpload(bannerImage, bannersDTO);
			bannersRepository.updateBanner(bannersDTO);
		} catch (IllegalStateException e) {
			log.error("파일 업로드 실패", e);
			throw new RuntimeException("파일 처리 실패", e);
		} catch (Exception e) {
			log.error("데이터베이스 업데이트 실패", e);
			throw new RuntimeException("데이터베이스 업데이트 실패", e);
		}
	}
	
	@Transactional
	public void insertBanner(BannersDTO bannersDTO, MultipartFile bannerImage) {
		try {
			imageUpload(bannerImage, bannersDTO);
			bannersRepository.insertBanner(bannersDTO);
		} catch (IllegalStateException e) {
			log.error("파일 업로드 실패", e);
			throw new RuntimeException("파일 처리 실패", e);
		} catch (Exception e) {
			log.error("데이터베이스 등록 실패", e);
			throw new RuntimeException("데이터베이스 등록 실패", e);
		}
	}
	
    public void deleteBanners(List<Integer> bannerNums) {
        for (Integer bannerNum : bannerNums) {
            bannersRepository.deleteBanner(bannerNum);
        }
    }
    
    private void imageUpload(MultipartFile bannerImage, BannersDTO bannersDTO) throws IOException {
        if (bannerImage != null && !bannerImage.isEmpty()) {
        	String originalFilename = "original";
        	String changedFileName = String.format("%s-%s", System.currentTimeMillis(), originalFilename);

            bannersDTO.setBanner_original(originalFilename);
            bannersDTO.setBanner_changed(changedFileName);
        }
    }
}