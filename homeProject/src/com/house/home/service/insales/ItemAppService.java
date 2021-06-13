package com.house.home.service.insales;

import java.util.Date;
import java.util.List;
import java.util.Map;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.Customer;
import com.house.home.entity.insales.ItemApp;

public interface ItemAppService extends BaseService {

	/**ItemApp分页信息
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemApp itemApp);
	
	/**ItemApp_code分页信息
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySqlCode(Page<Map<String,Object>> page, ItemApp itemApp);

	/**ItemApp明细分页信息
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findDetailBySql(Page<Map<String,Object>> page, ItemApp itemApp);
	
	/**
	 * 调用存储过程操作领料信息（新增、修改、审核）
	 * @param itemApp
	 * @return
	 */
	public Result doItemAppForProc(ItemApp itemApp);
	
	/**
	 * 调用存储过程操作领料退回（新增、修改、审核）
	 * @param itemApp
	 * @return
	 */
	public Result doReturnItemAppForProc(ItemApp itemApp);	
	
	/**
	 * 调用存储过程操作供应商直送、仓库发货
	 * @param itemApp
	 * @return
	 */
	public Result doSendItemAppForProc(ItemApp itemApp);
	
	/**
	 * 调用存储过程操作仓库分批发货
	 * @param itemApp
	 * @return
	 */
	public Result doPSendItemAppForProc(ItemApp itemApp);

	public Map<String,Object> getByNo(String id);

	/**
	 * 领料退回
	 * @param itemApp
	 * @return
	 */
	public Result doItemAppBackForProc(ItemApp itemApp);

	/**
	 * 可选取退货明细列表
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_itemReturn(Page<Map<String,Object>> page, ItemApp itemApp);
	/**
	 * 根据预领料编号查询领料申请
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageByPreAppNo(Page<Map<String,Object>> page, String id);
	/**
	 * 计算是否需要自动生成预计到货日期
	 * @param itemApp
	 * @return
	 */
	public boolean calcIsAutoArriveDate(String supplCode);
	/**
	 * 根据规则计算预计到货日期
	 * @param itemApp
	 * @return
	 */	
	public Page<Map<String, Object>> findPageByProductType(Page<Map<String, Object>> page, ItemApp itemApp);
	/**
	 * 计算是否发货超时
	 * @param itemApp
	 * @return
	 */
	public boolean calcIsTimeout(ItemApp itemApp);
	/**
	 * 客户信息查询领料单列表
	 * @param page
	 * @param id
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_khxxcx(Page<Map<String,Object>> page, ItemApp itemApp);
	/**明细查询
	 * @param page
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> findDetailListBySql(Page<Map<String,Object>> page, ItemApp itemApp);
	
	public Page<Map<String,Object>> goJqGridItemApp(Page<Map<String,Object>> page, ItemApp itemApp);

	public Page<Map<String,Object>> getReturnAddJqGrid(Page<Map<String,Object>> page, String oldNo,String reqPks,String custCode);

	public Page<Map<String,Object>> getReturnDetailJqGrid(Page<Map<String,Object>> page, String itemCode,String custCode);
	
	public Map<String,Object> getWHOperator(String czybh,String whcode);

	public Result doLlglthSave(ItemApp itemApp, String xml);
	
	public Map<String,Object> getItemAppInfo(String no);
	
	public Map<String,Object> getItemReturnInfo(String no);

	public Page<Map<String,Object>> getItemReturnDTJqGrid(Page<Map<String,Object>> page, String no,String custCode);
	
	public Map<String,Object> getCZYGNQX(String czybh,String mkdm,String gnmc);
	
	public Double getSendQtyByNo(String no);
	
	public Map<String,Object> doUnCheck(String no);

	public Page<Map<String,Object>> getSendAppDtlJqGrid(Page<Map<String,Object>> page, String no,String custCode);
	
	public Result doSendForXml(String m_umState,String no,String whcode,Date sendDate,String itemSendBatchNo,String supplCode,String czybh, String xml, String manySendRsn,String delivType,String delayReson,String delayRemark);

	public Page<Map<String,Object>> getPSendAppDtlJqGrid(Page<Map<String,Object>> page, String no,String custCode);
	
	public Result doSendBatchForXml(String no,String whcode,String itemType1,String custCode,Date sendDate,String remarks,String itemSendBatchNo,String czybh, String xml, String manySendRsn,String delayReson,String delayRemark, String delivType);
	
	public boolean isSendPartNo(String no);

	public Page<Map<String,Object>> getShortageJqGrid(Page<Map<String,Object>> page, String no,String custCode);
	
	public Result doShortage(String no,String czybh, String xml);
	
	public Result doSendMemo(String no,Date arriveDate,String delivType,String splRemarks,String czybh);
	
	public boolean isExistSendBatch(String no,String sendBatchNo,String custCode);

	public Page<Map<String,Object>> goUnCheckJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, Date appDateFrom, Date appDateTo, String mainManCode);

	public Page<Map<String,Object>> goConfirmReturnJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String mainManCode2);

	public Page<Map<String,Object>> goUnReceiveBySplJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String supplCode, Date confirmDateFrom, Date confirmDateTo, String mainManCode,String delivType);

	public Page<Map<String,Object>> goUnSendBySplJqGrid(Page<Map<String,Object>> page, String itemType1, String itemType2, String supplCode, Date arriveDateFrom, Date arriveDateTo, String mainManCode,String prjRegion,String region,String delivType,Date notifySendDateFrom, Date notifySendDateTo);

	public Page<Map<String,Object>> goDetailQueryJqGrid(Page<Map<String,Object>> page, ItemApp itemApp);
	
	public Page<Map<String,Object>> goJqGridLlglSendList(Page<Map<String,Object>> page, ItemApp itemApp);
	
	public Page<Map<String,Object>> goJqGridFhDetail(Page<Map<String,Object>> page, String fhNo);
	
	public Result doSaveArriveConfirm(String no, String projectManRemark, String czybh);
	
	public Result getPhoneMessage(String module, String custCode, String no, String czybh);
	
	public List<Map<String,Object>> getPhoneList(String custCode);
	
	public Result doSaveSendSMS(String no, String message, String czybh, String xml);
	
	public Page<Map<String,Object>> goJqGridPrintBatch(Page<Map<String,Object>> page, ItemApp itemApp);
	
	public boolean getBeforeDoPrintZX(String no);
	
	public boolean getBeforeDoPrintWFH(String no, String checkFH, String whcode);
	
	/**
	 * 结束代码为4.施工取消，如果有材料已发货数量不为0，提示“该楼盘已发过材料”，且不允许通过
	 * @param custCode
	 * @return
	 */
	public double getTotalQty(String custCode);
	
	public Page<Map<String,Object>> goJqGridDishesSend(Page<Map<String,Object>> page, ItemApp itemApp);
	
	public String isExistFSGR(String custCode);
	/**
	 * 提交审核/验证成本控制规则
	 * @param custCode
	 * @param appNo
	 * @param m_umState
	 * @param czy
	 * @param isPayCtrl
	 * @return
	 */
	public Result doSubmitCheck(String custCode,String appNo,String m_umState,String czy,
			String isPayCtrl,String isCupboard,String itemType1,String itemType2,String custType,String chgNo);
	/**
	 * 供应商下单金额是否小于我们下单金额
	 * 
	 * @return
	 */
	public List<Map<String, Object>> checkCost(String no);
	/**
	 * 供应商下单金额是否小于我们下单金额直接通过审核
	 * 
	 * @param itemApp
	 */
	public void doSuplCheck(ItemApp itemApp);
	/**
	 * 是否已申请水电材料
	 * @param custCode
	 * @return
	 */
	public String isExistJZSD(String custCode, String itemType2);
	/**
	 * 采购费用明细
	 * @param page
	 * @param puno
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridPuFeeDetail(Page<Map<String,Object>> page, String puno);
	/**
	 * 结算申请
	 * @param itemApp
	 * @return
	 */
	public Result doCheckApp(ItemApp itemApp);
	/**
	 * 结算申请-材料明细
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> goJqGridItemDetail(Page<Map<String,Object>> page, ItemApp itemApp);
	/**
	 * 退管管理-结算申请保存
	 * 
	 * @param itemApp
	 */
	public void doCheckAppR(ItemApp itemApp);
	/**
	 * 存在下单数量为0的材料
	 * 
	 * @return
	 */
	public List<Map<String, Object>> hasZero(String no);
	/**
	 * 供货管理--不能安装明细
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> goNotInstallDetailJqGrid(Page<Map<String,Object>> page, Customer customer);
	/**
	 * 供货管理--补货明细
	 * @param itemApp
	 * @return
	 */
	public Page<Map<String,Object>> goIntReplenishDetailJqGrid(Page<Map<String,Object>> page, Customer customer);
	/**
	 * 委托加工登记保存
	 * @author	created by zb
	 * @date	2020-1-1--下午4:04:14
	 * @param itemApp
	 * @param czybh 
	 */
	public void updateEntrustProcess(ItemApp itemApp, String czybh);
	
	public Result doWhReceive(ItemApp itemApp);
	
	/**
	 * 加工单数
	 * 
	 * @return
	 */
	public List<Map<String, Object>> hasCutNum(String no);
	
	/**
	 * 是否存在未加工完成的领料明细
	 * 
	 * @return
	 */
	public List<Map<String, Object>> isComplete(String no,String pks);

	public String isMaterialSendJob(ItemApp ia);
	
	/**
	 * 获取期数和差额
	 * @param no
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> getBalance(String no,String custCode);
}
