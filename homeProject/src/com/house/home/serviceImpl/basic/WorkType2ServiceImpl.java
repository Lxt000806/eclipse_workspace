package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.WorkType2Dao;
import com.house.home.entity.basic.WorkType2;
import com.house.home.service.basic.WorkType2Service;

@SuppressWarnings("serial")
@Service
public class WorkType2ServiceImpl extends BaseServiceImpl implements WorkType2Service {

	@Autowired
	private WorkType2Dao workType2Dao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkType2 workType2) {
		return workType2Dao.findPageBySql(page, workType2);
	}

	@Override
	public Page<Map<String,Object>> getPreWorkCostWorkType2(Page<Map<String, Object>> page, WorkType2 workType2){
		return workType2Dao.getPreWorkCostWorkType2(page, workType2);
	}
}
