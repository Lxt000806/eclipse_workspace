package com.house.home.serviceImpl.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.AgainAwardDao;
import com.house.home.dao.project.GcjdmbDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.AgainAward;
import com.house.home.entity.project.AgainAwardDetail;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.entity.project.PrjProgTemp;
import com.house.home.service.project.AgainAwardService;
import com.house.home.service.project.GcjdmbService;

@SuppressWarnings("serial")
@Service
public class GcjdmbServiceImpl extends BaseServiceImpl implements GcjdmbService {
	
	@Autowired
	private GcjdmbDao gcjdmbDao;
	
	@Override
	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, PrjProgTemp prjProgTemp){
		return gcjdmbDao.goJqGrid(page, prjProgTemp);
	}
	
	@Override
	public Map<String, Object> isPrjItemExist(Integer pk, String tempNo, String prjItem){
		return gcjdmbDao.isPrjItemExist(pk, tempNo, prjItem);
	}
	
	@Override
	public List<Map<String, Object>> getTipEvents(String tempNo, String prjItem){
		return gcjdmbDao.getTipEvents(tempNo, prjItem);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridProgTempDt(Page<Map<String, Object>> page, String no,String prjItems, String custCode){
		return gcjdmbDao.goJqGridProgTempDt(page, no,prjItems, custCode);
	}
	
	@Override
	public Page<Map<String,Object>> goJqGridProgTempAlarm(Page<Map<String, Object>> page, String no){
		return gcjdmbDao.goJqGridProgTempAlarm(page, no);
	}
	
	@Override
	public Result doSavePrjProgTempForProc(PrjProgTemp prjProgTemp, String progTempDt, String progTempAlarm){
		return gcjdmbDao.doSavePrjProgTempForProc(prjProgTemp, progTempDt, progTempAlarm);
	}

	@Override
	public Result doUpdatePrjProgTempForProc(PrjProgTemp prjProgTemp, String progTempDt) {
		return gcjdmbDao.doUpdatePrjProgTempForProc(prjProgTemp,progTempDt);
	}

	@Override
	public void doUpdateDispSeq(String tempNo, int dispSeq) {
		gcjdmbDao.doUpdateDispSeq(tempNo, dispSeq);
	}
	
}
