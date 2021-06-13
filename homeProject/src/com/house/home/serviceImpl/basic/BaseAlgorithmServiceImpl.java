package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.BaseAlgorithmDao;
import com.house.home.entity.basic.BaseAlgorithm;
import com.house.home.service.basic.BaseAlgorithmService;

@SuppressWarnings("serial")
@Service 
public class BaseAlgorithmServiceImpl extends BaseServiceImpl implements BaseAlgorithmService {
	@Autowired
	private  BaseAlgorithmDao baseAlgorithmDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BaseAlgorithm baseAlgorithm) {
		return baseAlgorithmDao.findPageBySql(page, baseAlgorithm);
	}

	@Override
	public Result doSave(BaseAlgorithm baseAlgorithm) {
		return baseAlgorithmDao.doSave(baseAlgorithm);
	}

	@Override
	public Page<Map<String, Object>> findPrjTypePageBySql(
			Page<Map<String, Object>> page, BaseAlgorithm baseAlgorithm) {
		return baseAlgorithmDao.findPrjTypePageBySql(page, baseAlgorithm);
	}


	@Override
	public String getBaseAlgorithmByDescr(String descr) {
		// TODO Auto-generated method stub
		return  baseAlgorithmDao.getBaseAlgorithmByDescr(descr);
	}

	@Override
	public boolean hasDescr(BaseAlgorithm baseAlgorithm) {
		return baseAlgorithmDao.hasDescr(baseAlgorithm);
	}

}
