package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.SpcBuilderDao;
import com.house.home.entity.basic.SpcBuilder;
import com.house.home.entity.design.ResrCust;
import com.house.home.service.basic.SpcBuilderService;

@SuppressWarnings("serial")
@Service
public class SpcBuilderServiceImpl extends BaseServiceImpl implements SpcBuilderService {
	@Autowired
	private SpcBuilderDao spcBuilderDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SpcBuilder spcBuilder) {
		return spcBuilderDao.findPageBySql(page, spcBuilder);
	}

	@Override
	public Page<Map<String, Object>> findDelivPageBySql(
			Page<Map<String, Object>> page, String code,String builderNums) {
		return spcBuilderDao.findDelivPageBySql(page, code,builderNums);
	}
	
	@Override
	public Result doSave(SpcBuilder spcBuilder){
		
		return spcBuilderDao.doSave(spcBuilder);
	}
	
	@Override
	public Result doUpdate(SpcBuilder spcBuilder){
		return spcBuilderDao.doSave(spcBuilder);
	}

	@Override
	public SpcBuilder getByDescr(String descr) {
		return spcBuilderDao.getByDescr(descr);
	}
	
	
}
