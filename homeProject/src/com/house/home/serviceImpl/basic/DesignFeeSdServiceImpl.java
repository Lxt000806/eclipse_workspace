package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.DesignFeeSdDao;
import com.house.home.entity.basic.DesignFeeSd;
import com.house.home.service.basic.DesignFeeSdService;

@SuppressWarnings("serial")
@Service 
public class DesignFeeSdServiceImpl extends BaseServiceImpl implements DesignFeeSdService {
	@Autowired
	private  DesignFeeSdDao designFeeSdDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DesignFeeSd designFeeSd) {
		return designFeeSdDao.findPageBySql(page, designFeeSd);
	}

	@Override
	public DesignFeeSd getDesignFeeSdByDesignFee(Double DesignFee) {
		return designFeeSdDao.getDesignFeeSdByDesignFee(DesignFee);
	}

	@Override
	public DesignFeeSd getDesignFByPositCustT(DesignFeeSd designFeeSd) {
		return designFeeSdDao.getDesignFByPositCustT(designFeeSd);
	}


}
