package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.CustCheckAnalysisDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.CustCheckAnalysisService;
@Service
@SuppressWarnings("serial")
public class CustCheckAnalysisServiceImpl extends BaseServiceImpl implements
		CustCheckAnalysisService {
	@Autowired
	private CustCheckAnalysisDao custCheckAnalysisDao;
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String, Object>> page, Customer customer) {
		// TODO Auto-generated method stub
		return custCheckAnalysisDao.findPageBySql_pkhjsfx(page,customer);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridCheckDetail(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String role,
														String statistcsMethod, String department1, String department2, String custtype, String constructType, String isContainSoft){
		return custCheckAnalysisDao.goJqGridCheckDetail(page, dateFrom, dateTo, role, statistcsMethod, department1, department2, custtype, constructType, isContainSoft);
	}

	@Override
	public Page<Map<String,Object>> goJqGridReturnDetail(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, String role, String statistcsMethod, 
															String department1, String department2, String custtype, String constructType, String isContainSoft, int returnFlag){
		return custCheckAnalysisDao.goJqGridReturnDetail(page, dateFrom, dateTo, role, statistcsMethod, department1, department2, custtype, constructType, isContainSoft, returnFlag);
	}

}
