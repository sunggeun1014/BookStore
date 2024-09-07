package com.ezen.bookstore.admin.banners.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.banners.dto.BannersDTO;
import com.ezen.bookstore.admin.banners.repository.BannersRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class BannersService {

	private final BannersRepository bannersRepository;

	public List<BannersDTO> list() {
		return bannersRepository.getAll();
	}

	public BannersDTO detailList(Integer bannerNum) {
		return bannersRepository.getBannerDetail(bannerNum);
	}

	@Transactional
	public void updateBanner(BannersDTO bannersDTO) {
		bannersRepository.updateBanner(bannersDTO);
	}

	@Transactional
	public void insertBanner(BannersDTO bannersDTO) {
		bannersRepository.insertBanner(bannersDTO);
	}

	public void deleteBanners(List<Integer> bannerNums) {
		for (Integer bannerNum : bannerNums) {
			bannersRepository.deleteBanner(bannerNum);
		}
	}
	@Transactional
    public List<BannersDTO> findExpiredBanners(Date today) {
        return bannersRepository.findExpiredBanners(today);
    }
}