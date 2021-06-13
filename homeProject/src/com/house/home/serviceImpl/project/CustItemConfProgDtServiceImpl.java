package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CustItemConfProgDtDao;
import com.house.home.entity.project.CustItemConfProgDt;
import com.house.home.service.project.CustItemConfProgDtService;

@SuppressWarnings("serial")
@Service
public class CustItemConfProgDtServiceImpl extends BaseServiceImpl implements CustItemConfProgDtService {

	@Autowired
	private CustItemConfProgDtDao custItemConfProgDtDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustItemConfProgDt custItemConfProgDt){
		return custItemConfProgDtDao.findPageBySql(page, custItemConfProgDt);
	}

}
