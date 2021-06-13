package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CustItemConfDateDao;
import com.house.home.entity.project.CustItemConfDate;
import com.house.home.service.project.CustItemConfDateService;

@SuppressWarnings("serial")
@Service
public class CustItemConfDateServiceImpl extends BaseServiceImpl implements CustItemConfDateService {

	@Autowired
	private CustItemConfDateDao custItemConfDateDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfDate custItemConfDate){
		return custItemConfDateDao.findPageBySql(page, custItemConfDate);
	}

	@Override
	public CustItemConfDate getCustItemConfDate(String custCode,
			String itemTimeCode) {
		// TODO Auto-generated method stub
		return custItemConfDateDao. getCustItemConfDate(custCode,itemTimeCode);
	}

}
