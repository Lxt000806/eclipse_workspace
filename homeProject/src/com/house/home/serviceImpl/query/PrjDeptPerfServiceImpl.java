package com.house.home.serviceImpl.query;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.PrjDeptPerfDao;
import com.house.home.entity.basic.Department2;
import com.house.home.service.query.PrjDeptPerfService;

@Service
@SuppressWarnings("serial")
public class PrjDeptPerfServiceImpl extends BaseServiceImpl  implements PrjDeptPerfService {
	@Autowired
	private PrjDeptPerfDao prjDeptPerfDao;
	
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,Department2 department2,String orderBy,String direction,String statistcsMethod){
		return prjDeptPerfDao.findPageBySql(page, department2, orderBy, direction,statistcsMethod);
	}

	@Override
	public Page<Map<String,Object>> goConfBuildsJqGrid(Page<Map<String,Object>> page,String empCode,Date dateFrom,Date dateTo,String custType){
		return prjDeptPerfDao.goConfBuildsJqGrid(page,empCode,dateFrom,dateTo,custType);
	}

	@Override
	public Page<Map<String,Object>> goCheckBuildsJqGrid(Page<Map<String,Object>> page,String empCode,Date checkDateFrom,Date checkDateTo,String custType,String department2){
		return prjDeptPerfDao.goCheckBuildsJqGrid(page,empCode,checkDateFrom,checkDateTo,custType,department2);
	}

	@Override
	public Page<Map<String,Object>> goReOrderBuildsJqGrid(Page<Map<String,Object>> page,String empCode,Date dateFrom,Date dateTo,String custType,String department2){
		return prjDeptPerfDao.goReOrderBuildsJqGrid(page,empCode,dateFrom,dateTo,custType,department2);
	}
	
    @Override
    public Page<Map<String, Object>> goChangedPerformanceJqGrid(Page<Map<String, Object>> page,
            Map<String, String> postData) {
        return prjDeptPerfDao.goChangedPerformanceJqGrid(page, postData);
    }
	
	@Override
	public Page<Map<String, Object>> goConfBuildsOutJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		return prjDeptPerfDao.goConfBuildsOutJqGrid(page, dept2Code, dateFrom, dateTo, custType);
	}

	@Override
	public Page<Map<String, Object>> goConfBuildsInJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		return prjDeptPerfDao.goConfBuildsInJqGrid(page, dept2Code, dateFrom, dateTo, custType);
	}

	@Override
	public Page<Map<String, Object>> goCheckBuildsOutJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		return prjDeptPerfDao.goCheckBuildsOutJqGrid(page, dept2Code, dateFrom, dateTo, custType);
	}

	@Override
	public Page<Map<String, Object>> goCheckBuildsInJqGrid(
			Page<Map<String, Object>> page, String dept2Code, Date dateFrom,
			Date dateTo, String custType) {
		return prjDeptPerfDao.goCheckBuildsInJqGrid(page, dept2Code, dateFrom, dateTo, custType);
	}

}
