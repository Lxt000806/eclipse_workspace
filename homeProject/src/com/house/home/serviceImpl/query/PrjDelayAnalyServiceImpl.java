package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjDelayAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjDelayAnalyService;

@SuppressWarnings("serial")
@Service 
public class PrjDelayAnalyServiceImpl extends BaseServiceImpl implements PrjDelayAnalyService {
	@Autowired
	private PrjDelayAnalyDao prjDelayAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return prjDelayAnalyDao.findPageBySql(page, customer);
	}

	@Override
	public Map<String,Object>  getMoreInfo(String custCode) {
		// TODO Auto-generated method stub
		return prjDelayAnalyDao.getMoreInfo(custCode);
	}

	@Override
	public Integer getDelayDays(String custCode) {
		return prjDelayAnalyDao.getDelayDays(custCode);
	}
	
	

}
