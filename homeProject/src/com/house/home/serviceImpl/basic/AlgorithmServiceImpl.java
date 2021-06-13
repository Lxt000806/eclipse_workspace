package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.AlgorithmDao;
import com.house.home.entity.basic.Algorithm;
import com.house.home.service.basic.AlgorithmService;

@SuppressWarnings("serial")
@Service
public class AlgorithmServiceImpl extends BaseServiceImpl implements AlgorithmService {

	@Autowired
	private AlgorithmDao algorithmDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Algorithm algorithm){
		return algorithmDao.findPageBySql(page, algorithm);
	}

	@Override
	public boolean hasDescr(Algorithm algorithm) {
		return algorithmDao.hasDescr(algorithm);
	}

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, Algorithm algorithm) {
		return algorithmDao.findDetailPageBySql(page, algorithm);
	}

	@Override
	public Result doSave(Algorithm algorithm) {
		return algorithmDao.doSave(algorithm);
	}

	@Override
	public List<Map<String, Object>> checkIsCalCutFee(Algorithm algorithm) {
		return algorithmDao.checkIsCalCutFee(algorithm);
	}

}
