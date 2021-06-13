package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.IntProgDetailDao;
import com.house.home.entity.project.IntProgDetail;
import com.house.home.service.project.IntProgDetailService;

@SuppressWarnings("serial")
@Service
public class IntProgDetailServiceImpl extends BaseServiceImpl implements IntProgDetailService {

	@Autowired
	private IntProgDetailDao intProgDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, IntProgDetail intProgDetail){
		return intProgDetailDao.findPageBySql(page, intProgDetail);
	}

	@Override
	public Map<String, Object> getIntProgDetail(String custCode) {
		return intProgDetailDao.getIntProgDetail(custCode);
	}

	@Override
	public Map<String, Object> findDescr(String cbm, String id) {
		return intProgDetailDao.findDescr(cbm, id);
	}

}
