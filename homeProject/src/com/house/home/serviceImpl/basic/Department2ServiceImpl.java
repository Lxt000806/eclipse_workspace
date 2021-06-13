package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.Department2Dao;
import com.house.home.entity.basic.Department2;
import com.house.home.service.basic.Department2Service;

@SuppressWarnings("serial")
@Service
public class Department2ServiceImpl extends BaseServiceImpl implements Department2Service {

	@Autowired
	private Department2Dao department2Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department2 department2){
		return department2Dao.findPageBySql(page, department2);
	}
	
	public Department2 getByDesc2(String desc2){
		return department2Dao.getByDesc2(desc2);
	}

	public Department2 getByCode(String code){
		return department2Dao.getByCode(code);
	}
	
	public List<Department2> getByDepartment1(String code) {
		return department2Dao.getByDepartment1(code);
	}

	public List<Department2> findByNoExpired() {
		return department2Dao.findByNoExpired();
	}

	public List<Department2> getByDepType(String depType) {
		return department2Dao.getByDepType(depType);
	}

	@Override
	public void expiredChild(Department2 department2) {
		department2Dao.expiredChild(department2);
	}

	@Override
	public List<Department2> getDepartment2WithLeader() {
		return department2Dao.getDepartment2WithLeader();
	}
	

	
	
}
