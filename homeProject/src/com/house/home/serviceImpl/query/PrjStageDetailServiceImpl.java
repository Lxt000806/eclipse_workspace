package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjStageDetailDao;
import com.house.home.dao.query.PrjStageProgAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjStageDetailService;
import com.house.home.service.query.PrjStageProgAnalyService;

@Service
@SuppressWarnings("serial")
public class PrjStageDetailServiceImpl extends BaseServiceImpl implements PrjStageDetailService{

	@Autowired
	private PrjStageDetailDao prjStageDetailDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,Customer customer) {
		return prjStageDetailDao.findPageBySql(page,customer);
	
	}
	
}
