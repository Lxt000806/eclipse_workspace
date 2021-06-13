package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import com.house.home.dao.finance.SoftPerfDao;
import com.house.home.entity.basic.Employee;
import com.house.home.entity.finance.SoftPerf;
import com.house.home.service.finance.SoftPerfService;

@SuppressWarnings("serial")
@Service
public class SoftPerfServiceImpl extends BaseServiceImpl implements SoftPerfService {
	@Autowired
	private SoftPerfDao softPerfDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, SoftPerf softPerf) {

		return softPerfDao.findPageBySql(page, softPerf);
	}
	
	@Override
	public Page<Map<String, Object>> findListPageBySql(
			Page<Map<String, Object>> page, SoftPerf softPerf) {
		// TODO Auto-generated method stub
		return softPerfDao.findListPageBySql(page, softPerf);
	}

	@Override
	public Page<Map<String, Object>> findEmpInfoPageBySql(
			Page<Map<String, Object>> page, Employee employee) {
		// TODO Auto-generated method stub
		return softPerfDao.findEmpInfoPageBySql(page, employee);
	}
	
	@Override
	public Page<Map<String, Object>> findCountSoftPerPageBySql(
			Page<Map<String, Object>> page, SoftPerf softPerf) {
		// TODO Auto-generated method stub
		return softPerfDao.findCountSoftPerPageBySql(page,softPerf);
	}
	
	@Override
	public Page<Map<String, Object>> findReportPageBySql(
			Page<Map<String, Object>> page, SoftPerf softPerf) {
		// TODO Auto-generated method stub
		if("1".equals(softPerf.getCountType())){
			return softPerfDao.findReport_empPageBySql(page,softPerf);
		}else {
			return softPerfDao.findReport_teamPageBySql(page,softPerf);
		}
	}
	
	@Override
	public Page<Map<String, Object>> findReportDetailPageBySql(
			Page<Map<String, Object>> page, SoftPerf softPerf) {
		// TODO Auto-generated method stub
		if("1".equals(softPerf.getCountType())){
			return softPerfDao.findReportDetail_empPageBySql(page,softPerf);
		}else {
			return softPerfDao.findReportDetail_teamPageBySql(page,softPerf);
		}
	}
	
	@Override
	public String isExistsPeriod(String no,String beginDate) {
		// TODO Auto-generated method stub
		return softPerfDao.isExistsPeriod(no,beginDate);
	}
	@Override
	public void doSaveCount(String no) {
		// TODO Auto-generated method stub
		 this.softPerfDao.doSaveCount(no);
	}
	
	@Override
	public void doSaveCountBack(String no) {
		// TODO Auto-generated method stub
		 this.softPerfDao.doSaveCountBack(no);
	}
	
	@Override
	public String checkStatus(String no) {
		// TODO Auto-generated method stub
		return softPerfDao.checkStatus(no);
	}
	@Override
	public void doGetSoftPerDetail(String no,String lastUpdatedBy) {
		// TODO Auto-generated method stub
		this.softPerfDao.doGetSoftPerDetail(no,lastUpdatedBy);
	}
	
	@Override
	public List<Map<String, Object>> checkMonExists(String no, Integer mon) {
		
		return softPerfDao.checkMonExists(no, mon);
	}
	
	@Override
	public Map<String, Object> getSoftPerfDetail(Integer pk) {
		// TODO Auto-generated method stub
		return softPerfDao.getSoftPerfDetail(pk);
	}

	@Override
	public Page<Map<String, Object>> findCountSoftPerIndPageBySql(
			Page<Map<String, Object>> page, SoftPerf softPerf) {
		return softPerfDao.findCountSoftPerIndPageBySql(page, softPerf);
	}

	@Override
	public String getSoftPerfNoByMon(Integer mon) {
		return softPerfDao.getSoftPerfNoByMon(mon);
	}


}
