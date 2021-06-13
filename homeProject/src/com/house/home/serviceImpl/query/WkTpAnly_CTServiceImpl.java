package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.WkTpAnly_CTDao;
import com.house.home.entity.project.WorkType12;
import com.house.home.service.query.WkTpAnly_CTService;

@SuppressWarnings("serial")
@Service
public class WkTpAnly_CTServiceImpl extends BaseServiceImpl implements WkTpAnly_CTService {

	@Autowired
	private WkTpAnly_CTDao wkTpAnly_CTDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkType12 workType12) {
		// TODO Auto-generated method stub
		return wkTpAnly_CTDao.findPageBySql(page, workType12);
	}
	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, WorkType12 workType12,String layOut,String area) {
		// TODO Auto-generated method stub
		return wkTpAnly_CTDao.findDetailPageBySql(page, workType12,layOut,area);
	}
	
	
	
}
