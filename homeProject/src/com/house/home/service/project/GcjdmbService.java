package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.bean.WebPage;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.project.PrjProgTemp;

public interface GcjdmbService extends BaseService {

	public Page<Map<String, Object>> goJqGrid(Page<Map<String, Object>> page, PrjProgTemp prjProgTemp);
	
	public Map<String, Object> isPrjItemExist(Integer pk, String tempNo, String prjItem);
	
	public List<Map<String, Object>> getTipEvents(String tempNo, String prjItem);
	
	public Page<Map<String,Object>> goJqGridProgTempDt(Page<Map<String, Object>> page, String no,String prjItems, String custCode);
	
	public Page<Map<String,Object>> goJqGridProgTempAlarm(Page<Map<String, Object>> page, String no);
	
	public Result doSavePrjProgTempForProc(PrjProgTemp prjProgTemp, String progTempDt, String progTempAlarm);
	
	public Result doUpdatePrjProgTempForProc(PrjProgTemp prjProgTemp, String progTempDt);
	
	public void doUpdateDispSeq(String tempNo,int dispSeq);
}
