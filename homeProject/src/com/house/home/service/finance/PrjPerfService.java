package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.PrjPerf;
import com.house.home.entity.finance.SoftPerf;

public interface PrjPerfService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,PrjPerf prjPerf);

	public Page<Map<String,Object>> getCountPrjPerJqGrid(Page<Map<String,Object>> page,PrjPerf prjPerf);

	public Page<Map<String,Object>> getReportJqGrid(Page<Map<String,Object>> page,PrjPerf prjPerf);

	public String getNotify(String beginDate);
	
	public void doCalcPerf(String no,String lastUpdatedBy, String calcType) ;

	public Result savePrjPerf(PrjPerf prjPerf);

	public Result updatePrjPerf(PrjPerf prjPerf);
	
	public void doSaveCount(String no) ;
	
	public void doSaveCountBack(String no) ;

	public void doPerfChg(String no,String lastUpdatedBy) ;

}
