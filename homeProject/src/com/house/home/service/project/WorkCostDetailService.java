package com.house.home.service.project;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.project.WorkCost;
import com.house.home.entity.project.WorkCostDetail;

public interface WorkCostDetailService extends BaseService {

	/**WorkCostDetail分页信息
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCostDetail workCostDetail);
	/**
	 * 按账号汇总
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> findCardListBySql(Page<Map<String, Object>> page, WorkCostDetail workCostDetail);
	/**
	 * 统计单项工种余额、单项工种发包、单项工种累计支出、总发包余额、总发包、总支出
	 * 
	 * @param custCode,workType2
	 * @return
	 */
	public Map<String,Object>findCostByCodeWork(String custCode,String workType2 );
	/**
	 * 查询prj相关
	 * 
	 * @param custCode,workType2
	 * @return
	 */
	public Map<String,Object>findPrjByCodeWork(String custCode,String workType2 );
	/**
	 * 统计合同总价
	 * 
	 * @param custCode,workType2
	 * @return
	 */
	public Map<String,Object>findTotalAcountByCodeWork(String custCode,String workType2 );
	/**
	 * 统计已领工资
	 * 
	 * @param custCode,workType2
	 * @return
	 */
	public Map<String,Object>findGotAcountByCodeWork(String custCode,String workType2 );
	/**
	 * 统计预扣金额
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public Map<String,Object> findYukou( WorkCostDetail workCostDetail);
	/**
	 * 从id对应的表根据code查descr
	 * @param value,id
	 * @return
	 */
	public Map<String , Object>  findDescr(String value,String id);
	/**
	 * 满足查询时间的防水工资及补贴列表
	 * 
	 * @param year
	 * @param month
	 * @param custcodes
	 * @return
	 */
	public List<Map<String, Object>> findSubsidyByDate(String year,String month,WorkCostDetail workCostDetail);
	/**
	 * 基础人工成本明细分页查询
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql2(Page<Map<String, Object>> page, WorkCostDetail workCostDetail);
	/**
	 * 集成进度明细--取消
	 * @param pk
	 * @param lastUpdatedBy
	 * @param confirmRemark
	 * @param refPrePk
	 */
	public void cancel(String pk,String lastUpdatedBy,String confirmRemark,String refPrePk);
	/**
	 * 集成进度明细--恢复
	 * @param pk
	 * @param lastUpdatedBy
	 * @param confirmRemark
	 * @param refPrePk
	 */
	public void renew(String pk,String lastUpdatedBy,String confirmRemark,String refPrePk);
	/**
	 * 是否允许进行非预扣的工人工资审批
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> isAllowConfirm(WorkCostDetail workCostDetail);
	/**
	 * 质保金超出的工人
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> overQualityFeeWorker(WorkCostDetail workCostDetail);
	/**
	 * 优秀班组奖励累计总额超过单项人工发包额的10%
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> overCustCtrl(WorkCostDetail workCostDetail);
	/**
	 * 预扣领取金额超过 预扣金额-预扣已发放金额
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> overWithHold(WorkCostDetail workCostDetail);
	/**
	 * 查询状态为3取消的明细
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> hasStatus3(WorkCostDetail workCostDetail);
	/**
	 * 是否福州
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isFz();
	// 20200415 mark by xzp 获取权限的代码统一到CzybmService
//	/**
//	 * 是否有新增管理权限
//	 * 
//	 * @param workCostDetail
//	 * @return
//	 */
//	public List<Map<String, Object>> hasAddManageRight(WorkCostDetail workCostDetail);
	/**
	 * 该卡号是否已申请过其他工种类型1的工资
	 * 
	 * @param workCostDetail
	 * @return workType2
	 */
	public String isMultiWorkType1(WorkCostDetail workCostDetail);
	

	/**
	 * 工种类型1,2
	 * @param type
	 * @param pCode
	 * @param uc
	 * @return
	 */
	public List<Map<String,Object>> findWorkTypeByAuthority(int type,String pCode,UserContext uc);
	/**
	 * 基础人工成本明细--审核
	 * 
	 * @param pk
	 * @param col
	 * @param value
	 */
	public void doCheck(String pk, String col,String value );
	/**
	 * 基础人工成本明细--生成水电材料奖惩
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> createWaterItem(Page<Map<String, Object>> page, WorkCostDetail workCostDetail);
	/**
	 * 是否已生成水电材料奖惩
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> isCreatedWaterItem(WorkCostDetail workCostDetail);
	/**
	 * 定额工资明细
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goDeJqGrid(Page<Map<String, Object>> page, WorkCostDetail workCostDetail);
	/**
	 * 批量生成申请明细
	 * 
	 * @param year
	 * @param month
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> findBatchCrtDetailByDate(String year,String month, WorkCostDetail workCostDetail);
	/**
	 * 班组成员工资明细
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goMemberJqGrid(Page<Map<String, Object>> page, WorkCostDetail workCostDetail);
	/**
	 * 生成班组成员工资明细
	 * 
	 * @param workCostDetail
	 * @return
	 */
	public List<Map<String, Object>> getMemberSalary( WorkCostDetail workCostDetail);
	/**
	 * 工资出账处理保存
	 * @param workCostDetail
	 * @return
	 */
	public Result doWorkerCostProvide (WorkCostDetail workCostDetail);
	/**
	 * 出账查询
	 * 
	 * @param page
	 * @param workCostDetail
	 * @return
	 */
	public Page<Map<String, Object>> goCheckOutJqGrid(Page<Map<String, Object>> page, WorkCost workCost);
	/**
	 * 劳务分包公司
	 * @param cutCheckOut
	 * @return
	 */
	public List<Map<String, Object>> getLaborCompny();
}
