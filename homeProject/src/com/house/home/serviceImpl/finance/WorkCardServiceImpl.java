package com.house.home.serviceImpl.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.WorkCardDao;
import com.house.home.entity.finance.WorkCard;
import com.house.home.service.finance.WorkCardService;

@SuppressWarnings("serial")
@Service
public class WorkCardServiceImpl extends BaseServiceImpl implements WorkCardService{

	@Autowired
	private WorkCardDao workCardDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkCard workCard) {
		// TODO Auto-generated method stub
		return workCardDao.findPageBySql(page, workCard);
		
	}

	

	
}
