package com.house.home.serviceImpl.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.WorkType1Dao;
import com.house.home.dao.basic.WorkType2Dao;
import com.house.home.entity.basic.WorkType1;
import com.house.home.service.basic.WorkType1Service;

@SuppressWarnings("serial")
@Service
public class WorkType1ServiceImpl extends BaseServiceImpl implements WorkType1Service {

	@Autowired
	private WorkType1Dao workType1Dao;
	@Autowired
	private WorkType2Dao workType2Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType1 workType1){
		return workType1Dao.findPageBySql(page, workType1);
	}

	@Override
	public List<WorkType1> findByNoExpired() {
		return workType1Dao.findByNoExpired();
	}
	
	@Override
	public List<Map<String, Object>> findWorkType(int type,
			String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.workType1Dao.findWorkType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.workType2Dao.findWorkType2(param);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> findWorkTypeForWorker(int type,
			String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.workType1Dao.findWorkType1(param);
		}else if(type == 2){//人工类型1
			param.put("pCode", pCode);
			resultList = this.workType2Dao.findWorkType2ForWorker(param);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> findWorkTypeAll(int type,
			String pCode) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.workType1Dao.findWorkType1(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.workType2Dao.findWorkType2All(param);
		}
		return resultList;
	}
	@Override
	public List<Map<String, Object>> findOfferWorkType(int type, String pCode) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.workType1Dao.findWorkType1(param);
		}else if(type == 2){
			param.put("pCode", pCode);
			resultList = this.workType2Dao.findOfferWorkType2(param);
		}
		return resultList;
	}
	
}
