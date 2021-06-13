package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.query.PrjProblemAnalyDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProblem;
import com.house.home.service.query.PrjProblemAnalyService;

@SuppressWarnings("serial")
@Service 
public class PrjProblemAnalyServiceImpl extends BaseServiceImpl implements PrjProblemAnalyService {
	@Autowired
	private  PrjProblemAnalyDao prjProblemAnalyDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, PrjProblem prjProblem) {
		// TODO Auto-generated method stub
		return prjProblemAnalyDao.findPageBySql(page, prjProblem);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlWaitDeal(Page<Map<String, Object>> page, PrjProblem prjProblem) {
		// TODO Auto-generated method stub
		return prjProblemAnalyDao.findPageBySqlWaitDeal(page, prjProblem);
	}

	@Override
	public Page<Map<String, Object>> findPageBySqlView(Page<Map<String, Object>> page, PrjProblem prjProblem) {
		// TODO Auto-generated method stub
		return prjProblemAnalyDao.findPageBySqlView(page, prjProblem);
	}
}
