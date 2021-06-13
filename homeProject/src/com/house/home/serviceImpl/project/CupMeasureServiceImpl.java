package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CupMeasureDao;
import com.house.home.entity.project.CupMeasure;
import com.house.home.service.project.CupMeasureService;

@SuppressWarnings("serial")
@Service
public class CupMeasureServiceImpl extends BaseServiceImpl implements CupMeasureService {

	@Autowired
	private CupMeasureDao cupMeasureDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CupMeasure cupMeasure){
		return cupMeasureDao.findPageBySql(page, cupMeasure);
	}

	@Override
	public String confirm(CupMeasure cupMeasure) {
		return cupMeasureDao.confirm(cupMeasure);
	}

	@Override
	public Double getCupDownHigh(String custCode) {
		return cupMeasureDao.getCupDownHigh(custCode);
	}

	@Override
	public Double getCupUpHigh(String custCode) {
		return cupMeasureDao.getCupUpHigh(custCode);
	}
	
	@Override
	public Double getBathDownHigh(String custCode) {
		return cupMeasureDao.getBathDownHigh(custCode);
	}

	@Override
	public Double getBathUpHigh(String custCode) {
		return cupMeasureDao.getBathUpHigh(custCode);
	}

}
