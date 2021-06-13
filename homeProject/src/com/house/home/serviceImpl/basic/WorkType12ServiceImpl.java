package com.house.home.serviceImpl.basic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.basic.WorkType12Dao;
import com.house.home.entity.project.WorkType12;
import com.house.home.entity.project.WorkType12Item;
import com.house.home.service.project.WorkType12Service;

@SuppressWarnings("serial")
@Service
public class WorkType12ServiceImpl extends BaseServiceImpl implements WorkType12Service {

	@Autowired
	private WorkType12Dao workType12Dao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkType12 workType12){
		return workType12Dao.findPageBySql(page, workType12);
	}

	public List<WorkType12> findByNoExpired(){
		return workType12Dao.findByNoExpired();
	}
	
	public Map<String,Object> getWorkType12InfoByCode(String code){
		return workType12Dao.getWorkType12InfoByCode(code);
	}
	
	public List<Map<String,Object>> findWorkType12Dept(int type,String pCode,UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			resultList = this.workType12Dao.findWorkType12(param,uc);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.workType12Dao.findDeptByWorkType12(param);
		} 
		return resultList;
	}
	
	public void update(WorkType12 workType12){
	    workType12Dao.edit(workType12);
	}
	
	@Override
	public Map<String, Object> findViewInfo(String id){
	    return workType12Dao.view(id);
	}

    @Override
    public List<Map<String, Object>> findBefWorkType(String id) {
        return workType12Dao.findBefWorkType(id);
    }
    
    @Override
    public List<Map<String, Object>> findWorkType12Item(String id){
        return workType12Dao.findWorkType12Item(id);
    }
    
    @Override
    public void addWorkType12Item(WorkType12Item workType12Item){
        workType12Dao.merge(workType12Item);
    }
    
    @Override
    public void deleteWorkType12Item(Integer pk){
        WorkType12Item workType12Item = workType12Dao.get(WorkType12Item.class, pk);
        workType12Dao.delete(workType12Item);
    }

	@Override
	public Page<Map<String, Object>> getTechBySql(
			Page<Map<String, Object>> page, WorkType12 workType12) {
		return workType12Dao.getTechBySql(page, workType12);
	}

	@Override
	public List<Map<String, Object>> getTechWorkType12() {
		return workType12Dao.getTechWorkType12();
	}

	@Override
	public List<Map<String, Object>> getWorkType12List() {
		// TODO Auto-generated method stub
		return workType12Dao.getWorkType12List();
	}

	@Override
	public Page<Map<String, Object>> goQualityJqGrid(
			Page<Map<String, Object>> page, WorkType12 workType12) {
		return workType12Dao.goQualityJqGrid(page, workType12);
	}

	@Override
	public void doAddQlt(WorkType12 workType12) {
		workType12Dao.doAddQlt(workType12);
	}

	@Override
	public void doUpdateQlt(WorkType12 workType12) {
		workType12Dao.doUpdateQlt(workType12);
	}

	@Override
	public void doDeleteQlt(WorkType12 workType12) {
		workType12Dao.doDeleteQlt(workType12);
	}
}
