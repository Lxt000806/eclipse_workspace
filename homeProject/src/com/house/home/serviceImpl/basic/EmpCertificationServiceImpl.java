package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.EmpCertificationDao;
import com.house.home.entity.basic.EmpCertification;
import com.house.home.service.basic.EmpCertificationService;

@SuppressWarnings("serial")
@Service
public class EmpCertificationServiceImpl extends BaseServiceImpl implements EmpCertificationService {

	@Autowired
	private EmpCertificationDao certificationDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, EmpCertification empCertification) {
		// TODO Auto-generated method stub
		return certificationDao.findPageBySql(page, empCertification);
	}
}
