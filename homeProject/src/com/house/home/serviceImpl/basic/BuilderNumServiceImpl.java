package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BuilderNumDao;
import com.house.home.entity.basic.BuilderNum;
import com.house.home.service.basic.BuilderNumService;

@SuppressWarnings("serial")
@Service
public class BuilderNumServiceImpl extends BaseServiceImpl implements BuilderNumService{
	@Autowired
	private BuilderNumDao builderNumDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, BuilderNum builderNum) {
		return builderNumDao.findPageBySql(page, builderNum);
	}

	@Override
	public Page<Map<String, Object>> findNumByCode(
			Page<Map<String, Object>> page, String builderDelivCode) {
		return builderNumDao.findNumByCode(page, builderDelivCode);
	}

	@Override
	public void multiAdd(String qz, Integer beginNum, Integer endNum,
			String builderDelivCode, String lastUpdatedBy) {
		builderNumDao.multiAdd(qz, beginNum, endNum, builderDelivCode, lastUpdatedBy);
	}

	@Override
	public void deleteBuilderNum(BuilderNum builderNum) {
		builderNumDao.deleteBuilderNum(builderNum);
	}
}
