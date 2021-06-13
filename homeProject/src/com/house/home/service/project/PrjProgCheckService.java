package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.ModifyConfirmQueryEvt;
import com.house.home.client.service.evt.PrjProgCheckQueryEvt;
import com.house.home.client.service.evt.WorkerListEvt;
import com.house.home.entity.insales.Purchase;
import com.house.home.client.service.evt.PrjProgCheckUpdateEvt;
import com.house.home.client.service.evt.SitePreparationEvt;
import com.house.home.entity.project.PrjProgCheck;
import com.house.home.entity.project.ProgCheckPlanDetail;

public interface PrjProgCheckService extends BaseService {

	/**PrjProgCheck分页信息
	 * @param page
	 * @param prjProgCheck
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck);

	public Page<Map<String,Object>> findConfirmPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,UserContext uc);
	
	public Page<Map<String,Object>> findPrjCheckPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck);

	
	//    add by hc  工地巡检分析   2017/11/14   begin 
	public Page<Map<String,Object>> findCheckPageBySql(Page<Map<String,Object>> page, PrjProgCheck prjProgCheck,UserContext uc);
	public Page<Map<String,Object>> findPageBySqlTJFS(Page<Map<String,Object>> page,  PrjProgCheck prjProgCheck,String orderBy,String direction);
	
	//    add by hc  工地巡检分析   2017/11/14   end
	/**getRemainModifyTime信息
	 * @return 	通过No取到RemainModifyTime
	 */
	public Map<String,Object> getRemainModifyTime(String No);
	
	
	
	
	/**PrjProgCheck
	 * @param page
	 * @param findPageBySqlTJFS  检索不同的分页信息
	 * @return
	 */
	
	@SuppressWarnings("rawtypes")
	public Page<Map<String,Object>> findPageByLastUpdatedBy(Page page, String id,String address,String isModify,String custCode);

	public void addPrjProgCheck(PrjProgCheck prjProgCheck,ProgCheckPlanDetail progCheckPlanDetail,String isExecute,String pk);
	public void updatePrjProgCheck(PrjProgCheck prjProgCheck,PrjProgCheckUpdateEvt evt);

	public Map<String,Object> getByNo(String id, String czybh);
	/**
	 * 需要整改，并且未完成整改的本人的记录
	 */
	public  Page<Map<String,Object>> findPageByCzy(Page<Map<String,Object>> page, String czy);
	/**
	 * 需要整改，并且完成整改的本人的记录
	 */
	public  Page<Map<String,Object>> findModifiedPageByCzy(Page<Map<String,Object>> page, String czy,String address);
	/**
	 * 查询工地整改详情
	 */
	public  Map<String,Object> getSiteModifyDetailById(String id);
	/**
	 * 工地整改保存接口
	 */
	public  void saveSiteModify(PrjProgCheck prjProgCheck,String photoName);
	/**
	 * 重新整改
	 * @param prjProgCheck
	 */
	public void updateSiteModify(PrjProgCheck prjProgCheck);

	/**
	 * 获取是否整改列表
	 */
	@SuppressWarnings("rawtypes")
	public Page<Map<String, Object>> getModifyList(Page page,PrjProgCheckQueryEvt evt);
	/**
	 * 判断项目进度开始时间是否填写
	 */
	public List<Map<String,Object>> isBegin(String custCode,String prjItem);
	/**
	 * 获取整改验收列表
	 */
	@SuppressWarnings("rawtypes")
	public Page<Map<String, Object>> getModifyConfirmList(Page page,ModifyConfirmQueryEvt evt);
	
	/**
	 * 获取工地报备列表
	 */
	@SuppressWarnings("rawtypes") 
	public Page<Map<String,Object>> getSitePreparationList(Page page,SitePreparationEvt evt);
	/**
	 * 工地报备
	 */
	public String hasSitePrepartion(SitePreparationEvt evt);
	/**
	 * 工地报备详细
	 */
	public Map<String,Object> getSitePrepartionById(SitePreparationEvt evt);
	/**
	 * 获取工地结算
	 * @param custCode
	 * @return
	 */
	public Map<String,Object> getCustCheck(SitePreparationEvt evt);
	/**
	 * 获取工地相关的所有工人列表
	 * @param page
	 * @param evt
	 * @return
	 */
	public Page<Map<String,Object>> getCustWorkerList(Page page,WorkerListEvt evt);
	
	public Map<String,Object> getIntProgress(String custCode);
	
	public Map<String, Object> getRoomInfo(String custCode, String czybh);
	
	public Map<String,Object> getCustLoan(String custCode);
	
	public Page<Map<String,Object>> getRegionList(Page<Map<String,Object>> page);
	
	public Map<String,Object> getIntProduce(String custCode,String supplCode);

}
