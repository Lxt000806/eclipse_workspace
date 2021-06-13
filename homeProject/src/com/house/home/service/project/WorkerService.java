package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.AddWokerCostEvt;
import com.house.home.client.service.evt.WokerCostApplyEvt;
import com.house.home.client.service.evt.WorkerAppEvt;
import com.house.home.client.service.evt.WorkerSignInEvt;
import com.house.home.entity.project.CustWorker;
import com.house.home.entity.project.WorkSign;
import com.house.home.entity.project.Worker;

public interface WorkerService extends BaseService {

	/**worker分页信息
	 * @param page
	 * @param worker
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Worker worker);

    public Page<Map<String,Object>> findMemberPageBySql(Page<Map<String, Object>> page, Worker worker);
	
	/**
	 * 给work_list分页使用
	 * zb
	 * @param page
	 * @param worker
	 * @param userContext 操作角色
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlList(Page<Map<String,Object>> page, Worker worker, UserContext userContext);

	public Page<Map<String,Object>> findCodePageBySql(Page<Map<String,Object>> page, Worker worker);

	public Page<Map<String,Object>> findOnDoDetailPageBySql(Page<Map<String,Object>> page, String  workerCode,String department2);
	
	public Page<Map<String,Object>> findWorkerWorkType12PageBySql(Page<Map<String,Object>> page, String  workerCode);
	
	public Page<Map<String,Object>> findWorkType12PageBySql(Page<Map<String,Object>> page, String  workType12Stirngs);
	
	public Worker getByPhoneAndMm(String phone, String mm);
	public Worker getByPhone(String phone);
	
	public Page<Map<String,Object>> getSiteConstructList(Page<Map<String,Object>> page, String code,String status,String address);
	
	public Page<Map<String,Object>> getWorkerProblemList(Page<Map<String,Object>> page, Integer custWkPk);
	
	public Map<String,Object> getWorkerPrjItem(WorkerAppEvt evt);
	public Map<String,Object> getCustWorkInfo(String workerCode,String custCode,Integer pk);
	public boolean addWokerCost(AddWokerCostEvt evt);
	public Page<Map<String,Object>> getWokerCostApply(Page<Map<String,Object>> page,WokerCostApplyEvt evt);
	public List<Map<String,Object>> getWokerCostApply(String custCode,String salaryType,String workerCode,Integer custWkPk,String isWorkApp, String workType2, String workType12);
	public List<Map<String,Object>> getWokerCostApplyWorkType12(String custCode,String salaryType,String workerCode,Integer custWkPk,String isWorkApp, String workType2, String workType12);
	public Map<String,Object> getWorkSignInCount(Integer custWkPk,String custCode,String workerCode);
	public Map<String,Object> getPrjItem2MaxSeq(String workType12);
	
	public List<Map<String,Object>> findRegion(int type,String pCode);
	//add by hc  2017-12-04 begin  班组施工情况分析
	public Page<Map<String,Object>> findworkTypeConstructDetail(Page<Map<String,Object>> page, Worker worker,String orderBy,String direction);
	public Page<Map<String,Object>> findbuilder(Page<Map<String,Object>> page, Worker worker);
	public Page<Map<String,Object>> findarrange(Page<Map<String,Object>> page, Worker worker);
	public Page<Map<String,Object>> findcomplete(Page<Map<String,Object>> page, Worker worker);
	public Page<Map<String,Object>> findconfirmAmount(Page<Map<String,Object>> page, Worker worker);
	public Page<Map<String,Object>> findcrtDate(Page<Map<String,Object>> page, Worker worker);
	public Page<Map<String,Object>> findnoPass(Page<Map<String,Object>> page, Worker worker);
	public List<Map<String,Object>> getWorkType12(String czyBH); 
	/**getWorkType12信息
	 * @return 	通过czybh取到workType12
	 */
	// add by hc 2017/12/04 end    班组施工情况分析
	
	public Map<String,Object> getCanApplyTimes(AddWokerCostEvt evt);
	
	public String getBefWorkType12Emp(String code,String workType12);
	
	public List<Map<String, Object>> getWorkPrjItemList(Integer custWkPk);
	
	public Map<String, Object> existPrjProgConfirm(Integer custWkPk);

	/**
	 * 查询idNum是否重复
	 * zb
	 * @param idnum
	 * @return
	 */
	public Worker getByIdnum(String idnum);

	/**
	 * 工人信息表储存过程
	 * @author	created by zb
	 * @date	2018-7-17--下午1:55:33
	 * @param worker
	 * @return
	 */
	public Result doSave(Worker worker);
	
	public Page<Map<String, Object>> getItemBatchList(Page<Map<String,Object>> page,
			String itemType1,int custWkPk);
	
	public List<Map<String,Object>> getRatedSalaryList(CustWorker custWorker,String appType);
	
	public Map<String,Object> getRatedSalary(CustWorker custWorker,String appType);
	
	public Map<String,Object> getAllowGetMatrail(CustWorker custWorker);	
	
	public void saveWorkSign(WorkSign workSign,String photoList,String photoCodeList);
	
	public Map<String,Object> getBefSameWorker(CustWorker custWorker);	
	
	public Map<String,Object> getAppAmount(CustWorker custWorker);
	
	public Map<String, Object> getSalaryCtrl(CustWorker custWorker);
	
	public List<Map<String, Object>> getTechPhotoList(CustWorker custWorker);
	
	public List<Map<String, Object>> getNotCompeletePrjItem(WorkerAppEvt evt);
	/**
	 * 工种分类2根据tPrjItem2人工工资进行选择
	 * @author	created by zb
	 * @date	2019-7-4--下午3:07:22
	 * @param evt
	 * @return
	 */
	public List<Map<String, Object>> getWorkType2(WorkerAppEvt evt);

	public String getSeqNo(String tableName);
	
	public boolean getWorkSignByCustWkPk(int custWkPk);
	
	/**
	 * 预算防水面积
	 * @author cjg
	 * @date 2019-12-10
	 * @param evt
	 * @return
	 */
	public Double getWaterArea(WorkerSignInEvt evt);
	
	public Result saveWorkerWorkType12ForProc(Worker worker,String xmlData);
	
	public Map<String, Object> getAllowSecondSignIn(Integer custWkPk);
	
	public List<Map<String,Object>>  getWorkerRatedSalaryList(String custCode);
	
	public List<Map<String,Object>> getWorkerRatedSalary(CustWorker custWorker);
	
	public List<Map<String,Object>> getInstallInfoList(CustWorker custWorker);

    public boolean checkCanDeleteMember(String memberCode);
    
    public List<Map<String, Object>> getWorkSignPic(String no);

}
