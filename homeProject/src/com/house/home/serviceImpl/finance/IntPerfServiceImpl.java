package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.IntPerfDao;
import com.house.home.entity.finance.IntPerf;
import com.house.home.service.finance.IntPerfService;

@SuppressWarnings("serial")
@Service
public class IntPerfServiceImpl extends BaseServiceImpl implements IntPerfService {

	@Autowired
	private IntPerfDao intPerfDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntPerf intPerf){
		return intPerfDao.findPageBySql(page, intPerf);
	}

	@Override
	public void doSaveCountBack(String no) {
		intPerfDao.doSaveCountBack(no);
	}

	@Override
	public String checkStatus(String no) {
		return intPerfDao.checkStatus(no);
	}

	@Override
	public void doSaveCount(String no) {
		intPerfDao.doSaveCount(no);
	}

	@Override
	public String isExistsPeriod(String no, String beginDate) {
		return intPerfDao.isExistsPeriod(no, beginDate);
	}

}
