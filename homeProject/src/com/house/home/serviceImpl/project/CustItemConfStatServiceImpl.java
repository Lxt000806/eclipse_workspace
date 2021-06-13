package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CustItemConfStatDao;
import com.house.home.service.project.CustItemConfStatService;

@SuppressWarnings("serial")
@Service
public class CustItemConfStatServiceImpl extends BaseServiceImpl implements CustItemConfStatService {

	@Autowired
	private CustItemConfStatDao custItemConfStatDao;

	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Date dateFrom, Date dateTo,
			Date sdDateFrom ,Date sdDateTo, Date nsDateFrom, Date nsDateTo,String mainBusinessMan, String custType){
		return custItemConfStatDao.findPageBySql(page, dateFrom, dateTo,sdDateFrom ,sdDateTo,nsDateFrom, nsDateTo,mainBusinessMan, custType);
	}

}
