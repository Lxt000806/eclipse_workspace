package com.house.home.serviceImpl.commi;

import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.commi.CommiExprDao;
import com.house.home.entity.commi.CommiExpr;
import com.house.home.service.commi.CommiExprService;

@SuppressWarnings("serial")
@Service 
public class CommiExprServiceImpl extends BaseServiceImpl implements CommiExprService {
	@Autowired
	private  CommiExprDao commiExprDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CommiExpr commiExpr) {

		return commiExprDao.findPageBySql(page, commiExpr);
	}

	
}
