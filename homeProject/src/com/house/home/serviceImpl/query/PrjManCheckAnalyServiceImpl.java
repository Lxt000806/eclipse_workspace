package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjCtrlCheckAnalysisDao;
import com.house.home.dao.query.PrjManCheckAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.PrjCtrlCheckAnalysisService;
import com.house.home.service.query.PrjManCheckAnalyService;

@SuppressWarnings("serial")
@Service
public class PrjManCheckAnalyServiceImpl extends BaseServiceImpl implements PrjManCheckAnalyService {

	@Autowired
	private PrjManCheckAnalyDao prjManCheckAnalyDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return prjManCheckAnalyDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findDetailBySql(Page<Map<String, Object>> page, Customer customer) {
		return prjManCheckAnalyDao.findDetailBySql(page, customer);
	}

}
