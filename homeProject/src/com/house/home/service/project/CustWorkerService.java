package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.Worker;

public interface CustWorkerService extends BaseService{

	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustWorker custWorker,String czybh, UserContext uc);

	public Page<Map<String,Object>> findViewSignPageBySql(Page<Map<String,Object>> page, CustWorker custWorker,String czybh);
	
	public Page<Map<String,Object>> findPageByCustCode(Page<Map<String,Object>> page, String custCode);

	public Page<Map<String,Object>> findWorkerDetailPageBySql(Page<Map<String,Object>> page, CustWorker custWorker);
	
	public Page<Map<String,Object>> findWaterAftInsItemAppPageBySql(Page<Map<String,Object>> page, CustWorker custWorker);

	public Page<Map<String,Object>> getWorkSignPicBySql(Page<Map<String,Object>> page, CustWorker custWorker);

	public Map<String,Object> getPk(String actionLog);
	
	public CustWorker getByCode(String custCode,String workType12,String workerCode);
	
	public void doDelWorkerCode(String custWorkPk);
	
	public boolean getIsExistsWorkerArr(CustWorker custWorker);
	
	public void updateIsPushCust(CustWorker custWorker);
	
	public void updateIsPushCustAll(CustWorker custWorker);
	
	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, CustWorker custWorker);
	
	public List<Map<String, Object>> getWorkType12Dept(String workType12);
	
	public Page<Map<String,Object>> getWorkType12List(Page<Map<String,Object>> page,String czybh);
	
	public Page<Map<String,Object>> getPrjRegionList(Page<Map<String,Object>> page);
	
	public Page<Map<String,Object>> getWorkerList(Page<Map<String,Object>> page,Worker worker);
	
	public Page<Map<String,Object>> getCustWorkerList(Page<Map<String,Object>> page,CustWorker custWorker);
	
	public Page<Map<String,Object>> getCustWorkerAppList(Page<Map<String,Object>> page,CustWorker custWorker);
	
	public Page<Map<String,Object>> getAllCustomerList(Page<Map<String,Object>> page,CustWorker custWorker);
	
	public Page<Map<String,Object>> getDepartment2List(Page<Map<String,Object>> page);
	/**
	 * 查询工种施工进度（工程进度拖延分析用）
	 * @author	created by zb
	 * @date	2019-11-8--下午6:13:38
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Page<Map<String,Object>> findWorkDaysPageByCustCode(Page<Map<String, Object>> page,
			String custCode);
	/**
	 * 工地工人安排、工人申请管理，如果工种对应的开始施工节点开始时间为空，自动填写对应施工节点的开始时间。
	 * @author	created by zb
	 * @date	2019-11-26--下午4:44:26
	 * @param custWorker
	 */
	public void updateBeginDateByWorkType12(CustWorker custWorker);

    public Result doComplete(HttpServletRequest request,
            HttpServletResponse response, Integer workSignPk,
            UserContext userContext);

    public Result undoComplete(HttpServletRequest request,
            HttpServletResponse response, Integer workSignPk,
            UserContext userContext);
    
    public Page<Map<String,Object>> goLogJqGrid(Page<Map<String,Object>> page, CustWorker custWorker);
}
