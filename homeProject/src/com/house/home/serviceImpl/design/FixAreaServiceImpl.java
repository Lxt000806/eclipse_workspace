package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.FixAreaDao;
import com.house.home.entity.design.FixArea;
import com.house.home.service.design.FixAreaService;

@SuppressWarnings("serial")
@Service
public class FixAreaServiceImpl extends BaseServiceImpl implements FixAreaService {

	@Autowired
	private FixAreaDao fixAreaDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, FixArea fixArea){
		return fixAreaDao.findPageBySql(page, fixArea);
	}

	@Override
	public boolean isExisted(FixArea fixArea) {
		// TODO Auto-generated method stub
		return fixAreaDao.isExisted(fixArea);
	}

	@Override
	public  Result addtFixArea(FixArea fixArea) {
	
		return fixAreaDao.addtFixArea(fixArea);
	}

	@Override
	public Result insertFixArea(FixArea fixArea) {
		return fixAreaDao.insertFixArea(fixArea);
		
	}

	@Override
	public Map<String,Object> deleteFixArea(FixArea fixArea) {
		return fixAreaDao.deleteFixArea(fixArea);
		
	}

	@Override
	public void addRegular_FixArea(String custCode,String czy) {
		 fixAreaDao.addRegular_FixArea(custCode,czy);
		
	}

	@Override
	public void addItem_FixArea(String custCode, String itemAreaDesc,
			String czy, String itemType1) {
		fixAreaDao.addItem_FixArea(custCode,itemAreaDesc,czy,itemType1);
		
	}

	@Override
	public int getFixAreaPk(String itemType1, String custCode, String descr, Integer isService) {
		return fixAreaDao.getFixAreaPk(itemType1, custCode, descr, isService);
	}

}
