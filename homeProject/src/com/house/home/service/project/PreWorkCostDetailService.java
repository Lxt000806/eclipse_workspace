package com.house.home.service.project;

import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.PreWorkCostDetail;

public interface PreWorkCostDetailService extends BaseService {

	/**PreWorkCostDetail分页信息
	 * @param page
	 * @param preWorkCostDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql1(Page<Map<String,Object>> page, PreWorkCostDetail preWorkCostDetail,UserContext uc);

	public Page<Map<String,Object>> findPageBySql_forClient(Page<Map<String,Object>> page, PreWorkCostDetail preWorkCostDetail);

	public Map<String, Object> getByPk(Integer pk);

	public boolean canCommit(String custCode, String workType2);
	
	//  PW
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PreWorkCostDetail pWorkCostDetail,UserContext uc);	
	/**findPageBySql信息
	
	 * @return 检索
	 */
	
	public List<Map<String,Object>> getMsg(int PK); 
	/**getMsg信息
	 * @return 判断是否上传定位图 1代表已经上传，0代表未上传
	 */
	
	public Map<String,Object> getWorkType1(String workType2);
	/**getWorkType1信息
	 * @return 	通过worktype2取到workType1
	 */
	
	public Map<String,Object> getXTCS(String xtcsid);
	/**getWorkType1信息
	 * @return 	通过worktype2取到workType1
	 */
	
	public Map<String,Object> getzlqx(String czybh,String workerCode);
		/**getzlqx信息
	 * @return 	取到助理的权限，看是否有审核资格
	 */
	public Map<String,Object> getworkType12(String workerCode);
		/**getzlqx信息
	 * @return 	取到工种类型12的名称
	 */
	public Map<String,Object> getWorkCon(String custCode,String workType2);
	/**getWorkCon信息
	 * @param custCode 客户编号
	 * @param workType2 工种类型2
	 * @return WorkCon 合同总价
	 */
	
	public Map<String,Object> getAmount(String Yukou);
	/**getAmount信息
	 * @param WithHoldNo 预扣单号
	 
	 * @return Amount:预扣金额
	 */
	
	public List<Map<String,Object>> getret(String WithHoldNo); 
	/**get 2信息
	 * @param WithHoldNo 预扣单号
	 
	 * @return ret1:领取金额
	 */
	
	public List<Map<String,Object>> getPk(int i);
	/**getMaxPk信息
	 * @param PK PK
	 * @return 
	 * 		AllCustCtrl:总支出,AllCustCost:总支出,CustCtrl:工种发包
			CustCost:工种支出,AllLeaveCustCost:总发包余额,LeaveCustCost:工种余额
			ConfirmRemark:审批说明,ConfirmAmount:审批金额
	 */
	
	public List<Map<String,Object>> getCodeType(String CustCode,String WorkType2);
	/**getCodeType信息
	 * @param CustCode 客户编号 WorkType2 工种类型2
	 * @return 
	 * 		PrjItem:施工项目,EndDate:完成日期,ConfirmCZY:验收人,ConfirmDate:验收日期
	 */
	
	public List<Map<String,Object>> getNotNullCustCode(String CustCode);
	/**getCustCode信息
	 * @param CustCode 客户编号 
	 * @return 
	 * 		当客户编号不为空的时候
			AllCustCtrl:总发包,AllCustCost:总发包,AllLeaveCustCost:总发包金额
	 */
	
	public List<Map<String,Object>> getNotNullWorkType2(String CustCode,String WorkType2);
	/**getNotNullWorkType2信息
	 * @param CustCode 客户编号  WorkType2 工种类型2
	 * @return 
	 * 		当工种类型2不为空的时候
			AllCustCtrl:总发包,AllCustCost:总发包,AllLeaveCustCost:总发包金额
			如果工种类型2为0的话       工种发包、工种支出、工种余额、人工控制项      四项为0
	 */
	
	public Map<String,Object> getCfmAmount(String custCode,String workType2);
	/**getCfmAmount信息
	 * @param CustCode 客户编号  WorkType2 工种类型2
	 * @return 
	 * 		 CfmAmount:已审核工资
	 */
	
	public List<Map<String,Object>> findWorkTypeByAuthority(int type,String pCode,UserContext uc);
	
	/**
	 * 根据权限查询商品类型
	 * @param type
	 * @param pCode
	 * @return
	 */
	
	public Result doZhuLiSave(PreWorkCostDetail pWorkCostDetail); 
	
	/**
	 * 从预申请列表导入基础人工成本明细
	 * 
	 * @param page
	 * @param preWorkCostDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql2(Page<Map<String,Object>> page, PreWorkCostDetail preWorkCostDetail);
	
	
	public Map<String, Object> getCustWorkerInfo(String custCode, String workerCode);
	
	/**
	 * @Description:  根据工种分类2表中的worktype12检查工地工人信息表中是否存在对应数据
	 * @author	created by zb
	 * @date	2018-9-12--下午3:24:35
	 * @param workType2
	 * @return
	 */
	public boolean hasCustWork(String workType2);

	/**
	 * @Description:  获取定额工资明细
	 * @author	created by zb
	 * @date	2018-11-7--下午3:18:25
	 * @param page
	 * @param pWorkCostDetail
	 */
	public Page<Map<String,Object>> getQuotaSalaryJqGrid(Page<Map<String, Object>> page,
			PreWorkCostDetail pWorkCostDetail);

	/**
	 * 同工种1二级已审核的工资
	 * @param custCode
	 * @param workType1
	 * @return
	 */
	public Map<String,Object> getCfmAmountByWorkType1(String custCode,String workType1);
	/**
	 * 是否面向工人解单
	 * @param custCode
	 * @return
	 */
	public Map<String,Object> hasBaseCheckItemPlan(String custCode);
	/**
	 * 定额工资 
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public String getQuotaSalary( PreWorkCostDetail preWorkCostDetail);
	/**
	 * 工种类型1,2联动，同项目经理app的
	 * @author cjg
	 * @date 2020-1-14
	 * @param type
	 * @param pCode
	 * @param uc
	 * @return
	 */
	public List<Map<String,Object>> findWorkTypeByAuthorityForPrj(int type,String pCode,UserContext uc);
	
	public Page<Map<String,Object>> goWorkCostDetailJqGrid(Page<Map<String, Object>> page, PreWorkCostDetail pWorkCostDetail, String hasBaseCheckItemPlan);
}
