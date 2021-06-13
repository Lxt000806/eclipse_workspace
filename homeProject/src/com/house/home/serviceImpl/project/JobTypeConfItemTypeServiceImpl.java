package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.JobTypeConfItemTypeDao;
import com.house.home.entity.project.JobTypeConfItemType;
import com.house.home.service.project.JobTypeConfItemTypeService;

@SuppressWarnings("serial")
@Service
public class JobTypeConfItemTypeServiceImpl extends BaseServiceImpl implements JobTypeConfItemTypeService {

	@Autowired
	private JobTypeConfItemTypeDao jobTypeConfItemTypeDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, JobTypeConfItemType jobTypeConfItemType){
		return jobTypeConfItemTypeDao.findPageBySql(page, jobTypeConfItemType);
	}

	@Override
	public List<Map<String, Object>> getByJobTypeAndConfItemType(JobTypeConfItemType jobTypeConfItemType) {
		
		return jobTypeConfItemTypeDao.getByJobTypeAndConfItemType(jobTypeConfItemType);
	}

	

}
