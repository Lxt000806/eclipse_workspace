package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.WorkType12DeptDao;
import com.house.home.entity.basic.WorkType12Dept;
import com.house.home.service.basic.WorkType12DeptService;

@SuppressWarnings("serial")
@Service
public class WorkType12DeptServiceImpl extends BaseServiceImpl implements WorkType12DeptService{

	@Autowired
	private WorkType12DeptDao workType12DeptDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, WorkType12Dept workType12Dept) {
		// TODO Auto-generated method stub
		return workType12DeptDao.findPageBySql(page, workType12Dept);
	}

	@Override
	public boolean getIsExists(String code) {
		// TODO Auto-generated method stub
		return workType12DeptDao.getIsExists(code);
	}

	@Override
	public boolean getIsExistsDescr(String descr, String workType12) {
		// TODO Auto-generated method stub
		return workType12DeptDao.getIsExistsDescr(descr, workType12);
	}
	
	@Override
	public boolean getIsExistsDescrByCode(String code,String descr, String workType12) {
		// TODO Auto-generated method stub
		return workType12DeptDao.getIsExistsDescrByCode(code,descr, workType12);
	}

	@Override
	public Page<Map<String, Object>> getWorkType12DeptList(
			Page<Map<String, Object>> page, WorkType12Dept workType12Dept) {
		
		return workType12DeptDao.getWorkType12DeptList(page, workType12Dept);
	}

	
	
	
}
