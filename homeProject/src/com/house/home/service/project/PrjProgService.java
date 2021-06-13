package com.house.home.service.project;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.GetNoSendItemEvt;
import com.house.home.entity.basic.PrjItem1;
import com.house.home.entity.design.Customer;
import com.house.home.entity.project.PrjProg;
import com.house.home.entity.project.PrjProgPhoto;
import com.house.home.entity.project.ProgTempDt;

public interface PrjProgService extends BaseService {

	/**
	 * PrjProg分页信息
	 * 
	 * @param page
	 * @param prjProg
	 * @return
	 */
	public Page<Map<String, Object>> findUpdateStopPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg,UserContext uc);
	
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg);
	
	
	public Page<Map<String, Object>> findBuilderRepPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg);
	
	public Page<Map<String, Object>> findCustComplainPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg);
	
	public Page<Map<String, Object>> findConfirmPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg,UserContext uc);
	
	public Page<Map<String, Object>> findPrjProgUpdateJDPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg);
	
	public Page<Map<String, Object>> findPrjLogPageBySql(
			Page<Map<String, Object>> page, PrjProg prjProg);

	/**
	 * 根据项目经理查询进度列表
	 * 
	 * @param page
	 * @param projectMan
	 * @return
	 */
	public Page<Map<String, Object>> findPageByProjectMan(
			Page<Map<String, Object>> page, String projectMan);
	
	/**
	 * 根据客户编号查询进度明细列表
	 * 
	 * @param page
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, String code);

	/**
	 * 根据PK查询进度详情
	 * 
	 * @return
	 */
	public Map<String,Object> getPrjProgByPk(Integer pk);

	/**根据客户编号、工程编号和提醒日期类型查询提醒详情
	 * @param code
	 * @param prjItem
	 * @param dayType
	 * @return
	 */
	public Map<String, Object> getPrjProgAlarm(String code, String prjItem, String dayType);
	/**根据客户编号、工程编号查询工程进度
	 * @param code
	 * @param prjItem
	 * @return
	 */
	public Map<String, Object> getPrjProgByCodeAndPrjItem(String code, String prjItem);
	/**修改工程进度开始时间或结束时间
	 * @param pk
	 * @param dayType
	 * @param curDate
	 * @param czybh
	 * @return
	 */
	public Result updatePrjProgForProc(int pk,String dayType,Date curDate,String czybh,String custCode,String prjItem);

	
	public Result doUpdateCustStatus(PrjProg prjProg);
	/**
	 * 根据项目经理编号和对于客户号查询对于的工程延误和剩余工期
	 */
	public Map<String,Object> getDelayAndRemain(String projectMan,String code);
	
	public Result doPrjProgUpdate(PrjProg prjProg);

	public List<Map<String, Object>> getPrjProgByCodeAndPrjItemDescr(
			String custCode, String prjItemDescr);
	/**
	 * 根据客户Id获取当前节点
	 * @param page
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getPrjProgCurrentById(String custCode);
	
	/**
	 * 模板设定，修改施工项目开始时间和结算时间
	 * 
	 * */
	public void doSavePrjProg(String code,Date planBegin,Date planEnd, String lastUpDatedBy ,String tempNo,String prjProgTempType );

	public void doSavePrjProgBeginDate();

	/**
	 * 工程进度顺延
	 * 
	 * 
	 * */
	public void doPostPone(Integer postPoneDate,Integer postPoneEndDate,String custCode,Date planBegin);
	/**
	 * 删除巡检图片
	 * 
	 * */
	public void doDelPicture(String photoName);

	
	public Map<String,Object> getMaxPk(String custCode);
	
	
	public List<PrjItem1> getPrjItem1List();
	
	
	public Map<String,Object> isConfirm(String custCode);
	
	public void doUpdateConfirm(String custCode ,String prjItem,String confirmCZY,String ConfirmDesc,String prjLevel,String isPass);
	
	public void doReturnCheck(int pk,String czybh) ;
	
	public boolean getPrjProgPK(String code) ;
	
	public List<Map<String,Object>> getNoSendItem(GetNoSendItemEvt evt);
	
	public void updateIsPushCust(int pk,String isPustCust) ;
	
	public void updateIsPushCustAll(PrjProgPhoto prjProgPhoto) ;
	/**
	 * 计划进度编排保存
	 * @author cjg
	 * @date 2019-9-13
	 * @param prjProg
	 * @return
	 */
	public Result doProgArrange(PrjProg prjProg);
	
	public Page<Map<String, Object>> findLongTimeStopPageBySql(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findWaitFirstCheckPageBySql(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findWaitCustWorkAppPageBySql(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findTimeOutEndPageBySql(Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String,Object>> getJobOrderList(Page<Map<String,Object>> page, String custCode, String prjItem,String workType12);
	
	public Page<Map<String,Object>> getAlarmPrjItemList(Page<Map<String,Object>> page, String custCode);
	
	public Page<Map<String,Object>> getAlarmWorkType12List(Page<Map<String,Object>> page, String custCode);
	/**
	 * 获取工程进度模板明细
	 * @author	created by zb
	 * @date	2020-4-6--上午9:12:51
	 * @param progTempDt
	 * @return
	 */
	public ProgTempDt getProgTemDt(ProgTempDt progTempDt);
}
