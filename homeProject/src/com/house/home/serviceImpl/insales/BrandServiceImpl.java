package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.BrandDao;
import com.house.home.entity.insales.Brand;
import com.house.home.service.insales.BrandService;

@SuppressWarnings("serial")
@Service
public class BrandServiceImpl extends BaseServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Brand brand){
		return brandDao.findPageBySql(page, brand);
	}
	
	public List<Map<String,Object>> findBrand(Map<String,Object> param) {
		return this.brandDao.findBrand(param);
	}

	@Override
	public Page<Map<String, Object>> findBrandPageBySql(
			Page<Map<String, Object>> page, Brand brand) {
		return brandDao.findBrandPageBySql(page, brand);
	}

	@Override
	public Boolean checkDescr(Brand brand) {
		return brandDao.checkDescr(brand);
	}

}
