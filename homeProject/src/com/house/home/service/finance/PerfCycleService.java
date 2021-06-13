package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.PerfCycle;

public interface PerfCycleService extends BaseService {

	/**PerfCycle分页信息
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, PerfCycle perfCycle);
	/**
	 * 查询计算状态
	 * @param no
	 * @return
	 */
	public String checkStatus(String no);
	/**
	 * 检查周期
	 * @param no
	 * @param beginDate
	 * @return
	 */
	public String isExistsPeriod(String no,String beginDate);
	/**
	 * 检查业绩计算员工信息是否与当前员工信息一直
	 *
	 * @return
	 */
	public List<Map<String, Object>> checkEmployeeInfo();
	/**
	 * 计算完成
	 * @param no
	 */
	public void doComplete(String no);
	/**
	 * 退回
	 * @param no
	 */
	public void doReturn(String no);
	/**
	 * 员工信息同步列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String,Object>> findEmployeePageBySql(Page<Map<String,Object>> page, PerfCycle perfcycle);
	/**
	 * 员工信息同步
	 * 
	 * @param numbers
	 */
	public void doSyncEmployee(String numbers);
	/**
	 * 报表默认统计周期
	 *
	 * @return
	 */
	public List<Map<String, Object>> defaultCycle();
	/**
	 * 报表--明细
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportDetailBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--业务部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwbBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--设计部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSjbBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--事业部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSybBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--工程部
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportGcbBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--业务员
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwyBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--设计师
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSjsBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--翻单员
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportFdyBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--绘图员
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportHtyBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--业务主任
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwzrBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 已计算业绩
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findYjsyjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 未计算业绩
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findWjsyjBySql(Page<Map<String, Object>> page,PerfCycle perfCycle);
	/**
	 * 生成业绩数据
	 * 
	 * @param no
	 * @param lastUpdatedBy
	 * @param calChgPerf
	 */
	public Map<String, Object> doCount(String no, String lastUpdatedBy,String calChgPerf);
	/**
	 * 业绩扣减设置
	 * 
	 * @param perfCycle
	 */
	public void doPerfChgSet(PerfCycle perfCycle) ;
	/**
	 * 跳转到业绩扣减设置所需要的数据
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> findChgPefByCode(PerfCycle perfCycle);
	/**
	 * 跳转到指定客户所需要的数据
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> beforePointCust(PerfCycle perfCycle);
	/**
	 * 指定客户--参与业绩计算
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findCyyjjsBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 指定客户--不参与业绩计算
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findBcyyjjsBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 查是否计算业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> checkIsCalcPerf(PerfCycle perfCycle);
	/**
	 * 查所有客户状态,以逗号隔开
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> findAllCustType();
	/**
	 * 干系人列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findGxrBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 干系人修改历史列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findGxrxglsBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 基础增减列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findJczjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 材料增减列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findClzjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 合同费用增减列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findHtfyzjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 付款信息列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findFkxxBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 已算材料业绩
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getAlreadyMaterPerf(PerfCycle perfCycle);
	/**
	 * 付款方式、每平米材料业绩、面积
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getPayType(PerfCycle perfCycle);
	/**
	 * 客户原业绩的实际材料业绩
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getRegRealMaterPerf(PerfCycle perfCycle);
	/**
	 * 客户的增减实际业绩汇总
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getSumChgRealMaterPerf(PerfCycle perfCycle);
	/**
	 * 是否计算基础优惠
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getIsCalcBaseDisc(PerfCycle perfCycle);
	/**
	 * 原业绩达标时间
	 * 
	 * @param perfCycle
	 */
	public Map<String, Object> getRegAchieveDate(PerfCycle perfCycle);
	/**
	 * 原业绩pk
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getRegPerfPK(PerfCycle perfCycle);
	/**
	 * 查看原业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getRegPerformance(PerfCycle perfCycle);
	/**
	 * 导入原业绩列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findYyjBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 原业绩导入的数据
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getRegImport(PerfCycle perfCycle);
	/**
	 * 按业绩公式计算
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getExp(PerfCycle perfCycle);
	/**
	 * 业绩计算新增已计算业绩保存
	 * 
	 * @param perfCycle
	 * @return
	 */
	public Result doSaveProc(PerfCycle perfCycle);
	/**
	 * 修改是否复核
	 * 
	 * @param perfCycle
	 */
	public void changeIsCheck(PerfCycle perfCycle);
	/**
	 * 是否存在原业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> isExistRegPerfPk(PerfCycle perfCycle);
	/**
	 * 重签扣减业绩是否有对应的正常业绩/纯设计业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> isMatchedPerf(PerfCycle perfCycle);
	/**
	 * 是否存在纯设计转施工生成的业绩
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> isExistThisPerfPk(PerfCycle perfCycle);
	/**
	 * 计算增减生成的基础单项扣减
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> calcBaseDeduction(PerfCycle perfCycle);
	/**
	 * 计算增减生成的材料单品扣减
	 * 
	 * @param perfCycle
	 */
	public List<Map<String, Object>> calcItemDeduction(PerfCycle perfCycle);
	/**
	 * 报表--业务员(独立销售)
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwyDlxxBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	
	public void doBatchChecked(String sPK, String isCheck); 
	
	/**
	 * 部门领导信息同步列表
	 * 
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findLeaderPageBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 部门领导信息同步
	 * 
	 * @param codes
	 */
	public void doSyncLeader(String codes);
	/**
	 * 报表--业务团队
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportYwtdBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	/**
	 * 报表--设计团队
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findReportSjtdBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	
	/**
	 * 签单数据统计
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findSignDataJqGridBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	                                 
	/**
	 * 获取基础增减单套外增项
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getBaseChgSetAdd(PerfCycle perfCycle);
	
	/**
	 * 获取主材增减单毛利率
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getMainProPer_chg(PerfCycle perfCycle);
	
	/**
	 * 获取基础增减单木作预算、木作管理费
	 * @param perfCycle
	 */
	public List<Map<String, Object>> getBasePersonalPlan(PerfCycle perfCycle);
	
	/**
	 * 独立销售业绩
	 * @param page
	 * @param perfCycle
	 * @return
	 */
	public Page<Map<String, Object>> findIndependPerfBySql(Page<Map<String, Object>> page, PerfCycle perfCycle);
	
	
	
}
