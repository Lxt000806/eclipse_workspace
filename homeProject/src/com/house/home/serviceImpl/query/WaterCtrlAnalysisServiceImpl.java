package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.WaterCtrlAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.WaterCtrlAnalysisService;

@Service
@SuppressWarnings("serial")
public class WaterCtrlAnalysisServiceImpl extends BaseServiceImpl implements WaterCtrlAnalysisService{

	@Autowired
	private WaterCtrlAnalysisDao waterCtrlAnalysisDao;

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		if("1".equals(customer.getStatistcsMethod())){
			return waterCtrlAnalysisDao.findPageBySql(page, customer);
		}else{
			return waterCtrlAnalysisDao.findPageBySql_groupbyWorke(page, customer);
		}	
	}
}
