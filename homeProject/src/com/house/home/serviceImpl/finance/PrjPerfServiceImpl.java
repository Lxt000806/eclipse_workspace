package com.house.home.serviceImpl.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.finance.PrjPerfDao;
import com.house.home.entity.finance.PrjPerf;
import com.house.home.service.finance.PrjPerfService;

@SuppressWarnings("serial")
@Service 
public class PrjPerfServiceImpl extends BaseServiceImpl implements PrjPerfService {
	@Autowired
	private  PrjPerfDao prjPerfDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjPerf prjPerf) {
		return prjPerfDao.findPageBySql(page, prjPerf);
	}

	@Override
	public Page<Map<String, Object>> getCountPrjPerJqGrid(
			Page<Map<String, Object>> page, PrjPerf prjPerf) {
		return prjPerfDao.getCountPrjPerJqGrid(page, prjPerf);
	}
	
	@Override
	public Page<Map<String, Object>> getReportJqGrid(
			Page<Map<String, Object>> page, PrjPerf prjPerf) {
		return prjPerfDao.getReportJqGrid(page, prjPerf);
	}

	@Override
	public String getNotify(String beginDate) {
		return prjPerfDao.getNotify(beginDate);
	}

	@Override
	public void doCalcPerf(String no, String lastUpdatedBy, String calcType) {
		prjPerfDao.doCalcPerf(no, lastUpdatedBy, calcType);
	}

	@Override
	public Result savePrjPerf(PrjPerf prjPerf) {
		return prjPerfDao.savePrjPerf(prjPerf);
	}

	@Override
	public Result updatePrjPerf(PrjPerf prjPerf) {
		return prjPerfDao.savePrjPerf(prjPerf);
	}

	@Override
	public void doSaveCount(String no) {
		 this.prjPerfDao.doSaveCount(no);
	}
	
	@Override
	public void doSaveCountBack(String no) {
		 this.prjPerfDao.doSaveCountBack(no);
	}
	
	@Override
	public void doPerfChg(String no,String lastUpdatedBy) {
		 this.prjPerfDao.doPerfChg(no,lastUpdatedBy);
	}
	
}
