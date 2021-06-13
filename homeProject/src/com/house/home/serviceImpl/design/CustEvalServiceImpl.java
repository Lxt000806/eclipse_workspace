package com.house.home.serviceImpl.design;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.design.CustEvalDao;
import com.house.home.entity.design.CustEval;
import com.house.home.entity.design.ItemPlanTemp;
import com.house.home.service.design.CustEvalService;

@SuppressWarnings("serial")
@Service 
public class CustEvalServiceImpl extends BaseServiceImpl implements CustEvalService {
	@Autowired
	private  CustEvalDao custEvalDao;

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,CustEval custEval){
		return custEvalDao.findPageBySql(page, custEval);
	}

	
}
