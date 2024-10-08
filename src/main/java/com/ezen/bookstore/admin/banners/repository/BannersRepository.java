package com.ezen.bookstore.admin.banners.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.banners.dto.BannersDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class BannersRepository {

	private final SqlSessionTemplate sql;
	
	public List<BannersDTO> getAll(){
		return sql.selectList("Banners.getAll");
	}

	public BannersDTO getBannerDetail(Integer bannerNum) {
		return sql.selectOne("Banners.getDetail", bannerNum);
	}
	
	public void updateBanner(BannersDTO bannersDTO) {
		sql.update("Banners.updateBanner", bannersDTO);
	}

	public void insertBanner(BannersDTO bannersDTO) {
		sql.insert("Banners.insertBanner", bannersDTO);
	}
	
	public void deleteBanner(Integer bannerNum) {
		sql.delete("Banners.deleteBanner", bannerNum);
	}

}