package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustTypePerfExprDao;
import com.house.home.entity.basic.CustTypePerfExpr;
import com.house.home.service.basic.CustTypePerfExprService;

@SuppressWarnings("serial")
@Service
public class CustTypePerfExprServiceImpl extends BaseServiceImpl implements CustTypePerfExprService {

	@Autowired
	private CustTypePerfExprDao custTypePerfExprDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustTypePerfExpr custTypePerfExpr){
		return custTypePerfExprDao.findPageBySql(page, custTypePerfExpr);
	}

}
