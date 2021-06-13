package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.EmpExpDao;
import com.house.home.entity.basic.EmpExp;
import com.house.home.service.basic.EmpExpService;

@SuppressWarnings("serial")
@Service
public class EmpExpServiceImpl extends BaseServiceImpl implements EmpExpService {

	@Autowired
	private EmpExpDao empExpDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, EmpExp empExp) {
		// TODO Auto-generated method stub
		return empExpDao.findPageBySql(page, empExp);
	}
}
