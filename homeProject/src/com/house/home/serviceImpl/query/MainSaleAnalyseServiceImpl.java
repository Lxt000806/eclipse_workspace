package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.MainSaleAnalyseDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.MainSaleAnalyseService;
@Service
@SuppressWarnings("serial")
public class MainSaleAnalyseServiceImpl extends BaseServiceImpl implements MainSaleAnalyseService{
	
	@Autowired
	private MainSaleAnalyseDao mainSaleAnalyseDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer) {
		
		return mainSaleAnalyseDao.findPageBySql(page, customer);
	}
	@Override
	public Page<Map<String, Object>> findItemPlanSql(Page<Map<String, Object>> page, Customer customer) {
		return mainSaleAnalyseDao.findItemPlanSql(page, customer);
	}
	@Override
	public Page<Map<String, Object>> findChgDetailSql(Page<Map<String, Object>> page, Customer customer) {
		return mainSaleAnalyseDao.findChgDetailSql(page, customer);
	}
}
