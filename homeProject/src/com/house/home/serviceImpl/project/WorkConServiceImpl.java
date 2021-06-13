package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.WorkConDao;
import com.house.home.entity.project.WorkCon;
import com.house.home.service.project.WorkConService;

@SuppressWarnings("serial")
@Service
public class WorkConServiceImpl extends BaseServiceImpl implements WorkConService {

	@Autowired
	private WorkConDao workConDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCon workCon){
		return workConDao.findPageBySql(page, workCon);
	}

	@Override
	public List<Map<String, Object>> checkExist(WorkCon workCon) {
		return workConDao.checkExist(workCon);
	}

}
