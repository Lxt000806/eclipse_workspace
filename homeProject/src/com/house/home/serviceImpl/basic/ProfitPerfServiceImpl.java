package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ProfitPerfDao;
import com.house.home.entity.basic.ProfitPerf;
import com.house.home.service.basic.ProfitPerfService;

@SuppressWarnings("serial")
@Service
public class ProfitPerfServiceImpl extends BaseServiceImpl implements ProfitPerfService {

	@Autowired
	private ProfitPerfDao profitPerfDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ProfitPerf profitPerf) {
		// TODO Auto-generated method stub
		return profitPerfDao.findPageBySql(page, profitPerf);
	}
	

}
