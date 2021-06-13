package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.FixDutyEvt;
import com.house.home.entity.project.FixDuty;

public interface FixDutyService extends BaseService{
	
	public Page<Map<String,Object>> getFixDutyList(Page<Map<String,Object>> page,FixDutyEvt evt);
	
	public Page<Map<String,Object>> getFixDutyListForPrjMan(Page<Map<String,Object>> page,FixDutyEvt evt);
	
	public Page<Map<String,Object>> getFixDutyDetailList(Page<Map<String,Object>> page,FixDutyEvt evt);
	
	public Page<Map<String,Object>> getBaseCheckItemList(Page<Map<String,Object>> page,FixDutyEvt evt);
	
	public Result saveForProc(FixDuty fixDuty, String xml);
	public Page<Map<String,Object>> getFixDutyList(Page<Map<String,Object>> page, FixDuty fixDuty, UserContext uc);

	public Page<Map<String,Object>> getFixDutyDetail(Page<Map<String,Object>> page, FixDuty fixDuty);

	public Page<Map<String,Object>> getBaseCheckItemList(Page<Map<String,Object>> page, String baseCheckItemCode,String custCode);

	public Result doUpdateFixDutyDetail(FixDuty fixDuty,String xmlData);
	
	/**FixDuty分页信息
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, FixDuty fixDuty);
	/**
	 * 明细
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty);
	/**
	 * 责任人
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String, Object>> goManJqGrid(Page<Map<String, Object>> page, FixDuty fixDuty);
	/**
	 * 更新状态
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Long updateOneStatus(FixDuty fixDuty);
	/**
	 * 批量更新状态
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Result updateMultyStatus(FixDuty fixDuty);
	/**
	 * 取消
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public String cancel(FixDuty fixDuty);
	/**
	 * 保存
	 * 
	 * @param fixDuty
	 * @return
	 */
	public Result doSaveProc(FixDuty fixDuty);
	/**
	 * 明细查询
	 * 
	 * @param page
	 * @param fixDuty
	 * @return
	 */
	public Page<Map<String,Object>> goDetailQueryJqGrid(Page<Map<String,Object>> page, FixDuty fixDuty);
	
	public Page<Map<String,Object>> getPrjItemDescr(Page<Map<String,Object>> page, String czybh);
	
	public Page<Map<String,Object>> getBaseCheckItemDetailList(Page<Map<String,Object>> page,String custCode,String workType12);

	public List<Map<String, Object>> checkHasFixDuty(FixDuty fixDuty);
	
	public String updateFixDutyDetail(FixDuty fixDuty);
	
	public Map<String, Object> getFixDutyByCustCode(String custCode);
	
	public double getConfirmAmount(String custCode,String workerCode);
	/**
	 * 已存在工资单的定责单不能反审批。
	 * @author	created by zb
	 * @date	2019-7-25--下午5:37:33
	 * @param no
	 * @return
	 */
	public Boolean isWorkCostDetail(String no);
	/**
	 * 获取其他单累计风控金额
	 * @author cjg
	 * @date 2019-10-24
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getOtherRiskFund(String custCode,String no);
	/**
	 * 新增设计定责保存
	 * @author cjg
	 * @date 2019-10-25
	 * @param fixDuty
	 * @param xml
	 * @return
	 */
	public Result doSaveDeignDuty(FixDuty fixDuty);
	
	public void recoveryFixDuty(FixDuty fixDuty);
	
	public Map<String, Object> getFixDutyDetailInfo(String no);
	
	public Page<Map<String,Object>> getDepartment2List(Page<Map<String,Object>> page);
	
	/**
	 * 查询一个楼盘已有设计定责单数
	 * 
	 * @param fixDuty
	 * @return
	 */
	public long countDesignDuties(String custCode);
	
	/**
	 * 汇总一个楼盘的优惠使用信息
	 * 排除指定定责单
	 * 
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> summarizeDiscounts(String custCode, String fixDutyNo);
}
