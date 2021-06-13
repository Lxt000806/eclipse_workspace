package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.CustRecommendDao;
import com.house.home.entity.design.CustRecommend;
import com.house.home.service.basic.CustRecommendService;

@SuppressWarnings("serial")
@Service
public class CustRecommendServiceImpl extends BaseServiceImpl implements CustRecommendService {

	@Autowired
	private CustRecommendDao custRecommendDao;
	
	@Override
	public Page<Map<String, Object>> getCustRecommendList(
			Page<Map<String, Object>> page, CustRecommend custRecommend) {
		return custRecommendDao.getCustRecommendList(page, custRecommend);
	}

	@Override
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page,
			CustRecommend custRecommend, UserContext uc) {
		return custRecommendDao.goJqGrid(page,custRecommend,uc);
	}

	@Override
	public Page<Map<String, Object>> goCountJqGrid(
			Page<Map<String, Object>> page, CustRecommend custRecommend, UserContext uc) {
		return custRecommendDao.goCountJqGrid(page, custRecommend, uc);
	}

	@Override
	public CustRecommend getCustRecommendByCustPhone(String custPhone) {
		return custRecommendDao.getCustRecommendByCustPhone(custPhone);
	}

	@Override
	public  Map<String, Object> getCustRecommendDetail(int pk) {
		return custRecommendDao.getCustRecommendDetail(pk);
	}

}
