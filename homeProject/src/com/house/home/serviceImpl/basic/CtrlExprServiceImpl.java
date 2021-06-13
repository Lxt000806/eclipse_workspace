package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CtrlExprDao;
import com.house.home.entity.basic.CtrlExpr;
import com.house.home.service.basic.CtrlExprService;

@SuppressWarnings("serial")
@Service
public class CtrlExprServiceImpl extends BaseServiceImpl implements CtrlExprService {

	@Autowired
	private CtrlExprDao ctrlExprDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CtrlExpr ctrlExpr){
		return ctrlExprDao.findPageBySql(page, ctrlExpr);
	}

}
