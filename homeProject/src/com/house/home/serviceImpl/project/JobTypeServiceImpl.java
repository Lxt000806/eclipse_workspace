package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.JobTypeDao;
import com.house.home.entity.project.JobType;
import com.house.home.service.project.JobTypeService;

@SuppressWarnings("serial")
@Service
public class JobTypeServiceImpl extends BaseServiceImpl implements JobTypeService {

	@Autowired
	private JobTypeDao jobTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, JobType jobType){
		return jobTypeDao.findPageBySql(page, jobType);
	}
	public List<Map<String,Object>> getPrjJobTypeList(String itemRight){
		return jobTypeDao.getPrjJobTypeList(itemRight);
	}
	
	@Override
	public Page<Map<String, Object>> findERPPageBySql(
			Page<Map<String, Object>> page, JobType jobType) {
		return jobTypeDao.findERPPageBySql(page, jobType);
	}
	
}
