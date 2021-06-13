package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import sun.net.www.content.audio.wav;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustWorkerAppEvt;
import com.house.home.entity.design.Customer;
import com.house.home.entity.design.ResrCust;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.CustWorkerApp;
import com.house.home.entity.project.Worker;

public interface CustWorkerAppService extends BaseService{
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp,String czybh, UserContext uc);

	public Page<Map<String,Object>> findPrintPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> getReturnDetail(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> findWorkerDetailPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> findNoArrGRPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> findNoArrGdPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> findHasArrGdPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> findQQGZLPageBySql(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> findFSGZLPageBySql(Page<Map<String,Object>> page, Customer customer);
	
	public Page<Map<String,Object>> findSMGZLPageBySql(Page<Map<String,Object>> page,Customer customer);
	
	public Page<Map<String,Object>> findMZGZLPageBySql(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> findWorkerArrPageBySql(Page<Map<String,Object>> page, Customer customer);
	
	public Page<Map<String,Object>> findZFDetailPageBySql(Page<Map<String,Object>> page, Customer customer);

	public Page<Map<String,Object>> findItemArrPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);

	public Page<Map<String,Object>> findItemArrDetailPageBySql(Page<Map<String,Object>> page, Integer pk,String custCode);
	
	public Page<Map<String,Object>> findWorkTypeBefPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);
	
	public Page<Map<String,Object>> findCustWorkerAppPageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);
	
	public Page<Map<String,Object>> findAppNoArrangePageBySql(Page<Map<String,Object>> page, CustWorkerApp custWorkerApp);
	
	public Map<String, Object> getCustPk() ;
	
	public CustWorkerApp getByCode(String custCode,String workType12);

	public CustWorkerApp getByWorkerCode(String custCode,String workType12,String workerCode);

	
	public void doDel(Integer pk);

	public void doReturn(Integer pk);

	public void doCancel(Integer pk,String lastUpdatedBy);

	public Page<Map<String,Object>> getWorkerAppList(Page<Map<String,Object>> page,CustWorkerAppEvt evt);
	
	public boolean addWorkerApp(CustWorkerAppEvt evt);
	
	public Map<String,Object> getWorkerAppByPk(Integer pk);
	
	public boolean updateWorkerApp(CustWorkerAppEvt evt);
	
	public boolean judgeProgTemp(CustWorkerAppEvt evt);
	
/*	public boolean existWorkApp(CustWorkerAppEvt evt);*/
	
	public Map<String,Object> checkCustPay(CustWorkerAppEvt evt,String payNum);
	
	public Map<String, Object> getConstructDay(String custCode,String workerCode);
	
	public void doAutoArr(String lastUpdatedBy);

	public Map<String,Object> specialReqForApply(CustWorkerAppEvt evt);
	
	public Map<String,Object> getCustPayType(String custCode);
	
	public Map<String,Object> isSignEmp(String czybh);
	
	public String getWorkTypeConDay(String custCode,String workType12);
	
	public Map<String,Object> judgeBefWorkType12(String custCode, String workType12);
	
	public boolean getNeedWorkType2Req(String custCode,String workType12);
	
	public String isNeedZF(String custCode);
	
	public List<Map<String , Object>> befWorkType12Conf(String custCode,String workType12);

	public List<Map<String , Object>> itemSatisfy(String custCode,String workType12);
	
	public String getAppComeDate(String custCode,String workType12);
	
	/**
	 * 安排完工人更新消息为已读
	 * @param custCode
	 * @param workType12
	 */
	public void readMsg(CustWorkerApp custWorkerApp);
	
	public List<Map<String , Object>> getBefTaskComplete(CustWorkerApp custWorkerApp);
	
	public Page<Map<String,Object>> goDeJqGrid(Page<Map<String,Object>> page, CustWorker custWorker);
	
	/**
	 * 是否满足前置三个条件
	 * @param custCode
	 * @param workType12
	 * @param type
	 * @return
	 */
	public String getCanArr(String custCode,String workType12,String type );
	
	public Map<String, Object> getCustWorkerAppDataByCustWorkPk(Integer custWkPk);
}



















