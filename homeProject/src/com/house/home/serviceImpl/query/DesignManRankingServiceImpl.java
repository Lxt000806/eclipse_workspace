package com.house.home.serviceImpl.query;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.query.DesignManRankingDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DesignManRankingService;

@SuppressWarnings("serial")
@Service 
public class DesignManRankingServiceImpl extends BaseServiceImpl implements DesignManRankingService {
	@Autowired
	private  DesignManRankingDao designManRankingDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return designManRankingDao.findPageBySql(page, customer);
	}

}
