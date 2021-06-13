package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.Department3Dao;
import com.house.home.entity.basic.Department3;
import com.house.home.service.basic.Department3Service;

@SuppressWarnings("serial")
@Service
public class Department3ServiceImpl extends BaseServiceImpl implements Department3Service {

	@Autowired
	private Department3Dao department3Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department3 department3){
		return department3Dao.findPageBySql(page, department3);
	}
	
	public Department3 getByDesc2(String desc2){
		return department3Dao.getByDesc2(desc2);
	}

	public List<Department3> getByDepartment2(String code) {
		return department3Dao.getByDepartment2(code);
	}

	public List<Department3> findByNoExpired() {
		return department3Dao.findByNoExpired();
	}

	public Department3 getByCode(String code){
		return department3Dao.getByCode(code);
	}

}
