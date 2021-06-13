package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.LogWareEffAnalyDao;
import com.house.home.entity.insales.ItemWHBal;
import com.house.home.service.query.LogWareEffAnalyService;

@SuppressWarnings("serial")
@Service
public class LogWareEffAnalyServiceImpl extends BaseServiceImpl implements LogWareEffAnalyService {

	@Autowired
	private LogWareEffAnalyDao logWareEffAnalyDao;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemWHBal itemWHBal){
		return logWareEffAnalyDao.findPageBySql(page, itemWHBal);
	}
	@Override
	public Page<Map<String,Object>> findPageBySql_detail(Page<Map<String,Object>> page, ItemWHBal itemWHBal){
		return logWareEffAnalyDao.findPageBySql_detail(page, itemWHBal);
	}
	@Override
	public Page<Map<String, Object>> findPageBySql_sendDetail(
			Page<Map<String, Object>> page, ItemWHBal itemWHBal) {
		return logWareEffAnalyDao.findPageBySql_sendDetail(page, itemWHBal);
	}
	
}
