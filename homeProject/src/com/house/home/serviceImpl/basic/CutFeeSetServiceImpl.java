package com.house.home.serviceImpl.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.CutFeeSetDao;
import com.house.home.entity.basic.CutFeeSet;
import com.house.home.service.basic.CutFeeSetService;

@SuppressWarnings("serial")
@Service 
public class CutFeeSetServiceImpl extends BaseServiceImpl implements CutFeeSetService {
	@Autowired
	private  CutFeeSetDao cutFeeSetDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CutFeeSet cutFeeSet) {
		return cutFeeSetDao.findPageBySql(page, cutFeeSet);
	}

	@Override
	public boolean checkCode(String cutType, String size) {
		return cutFeeSetDao.checkCode(cutType, size);
	}

	@Override
	public Boolean doDelete(CutFeeSet cutFeeSet) {
		return cutFeeSetDao.doDelete(cutFeeSet);
	}

	@Override
	public Boolean doUpdate(CutFeeSet cutFeeSet) {
		return cutFeeSetDao.doUpdate(cutFeeSet);
	}

}
