package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjEffAnlyDao;
import com.house.home.service.query.PrjEffAnlyService;

@SuppressWarnings("serial")
@Service
public class PrjEffAnlyServiceImpl extends BaseServiceImpl implements PrjEffAnlyService {
	
	@Autowired
	private PrjEffAnlyDao prjEffAnlyDao;
	
	@Override
	public List<Map<String, Object>> goJqGrid(Date dateFrom, Date dateTo, String department2s, String custTypes, String sType, String builderCode){
		return prjEffAnlyDao.goJqGrid(dateFrom, dateTo, department2s, custTypes, sType, builderCode);
	}
	
	@Override
	public Page<Map<String, Object>> goJqGridView(Page<Map<String, Object>> page, Date dateFrom, Date dateTo, 
			String department2s, String custTypes, String dept2Code, String custType, String constructType, 
			String builderCode){
		return prjEffAnlyDao.goJqGridView(page, dateFrom, dateTo, department2s, custTypes, dept2Code, custType, constructType, builderCode);
	}
}
