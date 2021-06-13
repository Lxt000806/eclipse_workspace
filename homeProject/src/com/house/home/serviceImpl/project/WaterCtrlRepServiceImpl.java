package com.house.home.serviceImpl.project;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.WaterCtrlRepDao;
import com.house.home.entity.project.CustWorker;
import com.house.home.service.project.WaterCtrlRepService;

@SuppressWarnings("serial")
@Service
public class WaterCtrlRepServiceImpl extends BaseServiceImpl implements WaterCtrlRepService{
	@Autowired
	private WaterCtrlRepDao waterCtrlRepDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,CustWorker custWorker) {
		if ("1".equals(custWorker.getStatisticalMethods())) {
			return waterCtrlRepDao.findPageBySql(page,custWorker);
		} else if ("2".equals(custWorker.getStatisticalMethods())) {
			return waterCtrlRepDao.findPageByWorker(page, custWorker);
		}
		return waterCtrlRepDao.findPageBySql(page, custWorker);//默认按楼盘统计 modify by zb on 20200306
	}
	
	
	
}
