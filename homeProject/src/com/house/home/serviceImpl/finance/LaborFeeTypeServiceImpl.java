package com.house.home.serviceImpl.finance;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.finance.LaborFeeTypeDao;
import com.house.home.entity.finance.LaborFeeType;
import com.house.home.service.finance.LaborFeeTypeService;

@SuppressWarnings("serial")
@Service 
public class LaborFeeTypeServiceImpl extends BaseServiceImpl implements LaborFeeTypeService {
	@Autowired
	private  LaborFeeTypeDao laborFeeTypeDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, LaborFeeType laborFeeType) {
		return laborFeeTypeDao.findPageBySql(page,laborFeeType);
	}

	@Override
	public LaborFeeType getByCode(String code) {
		return laborFeeTypeDao.getByCode(code);
	}

}
