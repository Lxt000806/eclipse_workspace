package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CustItemConfProgDao;
import com.house.home.entity.project.CustItemConfProg;
import com.house.home.service.project.CustItemConfProgService;

@SuppressWarnings("serial")
@Service
public class CustItemConfProgServiceImpl extends BaseServiceImpl implements CustItemConfProgService {

	@Autowired
	private CustItemConfProgDao custItemConfProgDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfProg custItemConfProg){
		return custItemConfProgDao.findPageBySql(page, custItemConfProg);
	}

}
