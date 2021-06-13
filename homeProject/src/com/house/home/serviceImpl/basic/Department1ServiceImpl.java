package com.house.home.serviceImpl.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.Department1Dao;
import com.house.home.dao.basic.Department2Dao;
import com.house.home.dao.basic.Department3Dao;
import com.house.home.entity.basic.Department1;
import com.house.home.service.basic.Department1Service;

@SuppressWarnings("serial")
@Service
public class Department1ServiceImpl extends BaseServiceImpl implements Department1Service {

	@Autowired
	private Department1Dao deparment1Dao;
	@Autowired 
	private Department2Dao department2Dao;
	@Autowired 
	private Department3Dao department3Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Department1 deparment1){
		return deparment1Dao.findPageBySql(page, deparment1);
	}
	
	public Department1 getByDesc2(String desc2){
		return deparment1Dao.getByDesc2(desc2);
	}

	public List<Department1> findByNoExpired() {
		return deparment1Dao.findByNoExpired();
	}
	public List<Department1> getByDepType(String depType) {
		return deparment1Dao.getByDepType(depType);
	}
	
	public Department1 getByCode(String code) {
		return deparment1Dao.getByCode(code);
	}
	@Override
	public List<Map<String, Object>> findDepType(int type,
			String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.deparment1Dao.findDepType1(param);
		}else if(type == 2){
			param.put("pCode", pCode);
			resultList = this.department2Dao.findDepType2(param);
			}
		else if(type == 3	){
			param.put("pCode", pCode);
			resultList = this.department3Dao.findDepType3(param);
			}
		return resultList;
	}
	
	@Override
	public List<Map<String, Object>> findDepAll(int type, String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.deparment1Dao.findDep1All(param);
		}else if(type == 2){
			param.put("pCode", pCode);
			resultList = this.department2Dao.findDep2All(param);
			}
		else if(type == 3	){
			param.put("pCode", pCode);
			resultList = this.department3Dao.findDep3All(param);
			}
		return resultList;
	}

	@Override
	public void expiredChild(Department1 department1) {
		this.deparment1Dao.expiredChild(department1);
	}
	
}
