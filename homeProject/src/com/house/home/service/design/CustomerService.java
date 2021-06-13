package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.client.service.evt.CustomerQueryEvt;
import com.house.home.client.service.evt.GetCustomerEvt;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CommiCal;

public interface CustomerService extends BaseService {

	/**
	 * 分页查询客户信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);
	
	public Page<Map<String, Object>> findPageForOABySql(
			Page<Map<String, Object>> page, Customer customer,UserContext uc);
	
		/**
		 * 分页工程进度客户信息
		 * 
		 * @param page
		 * @param customer
		 * @return
		 */ 
		public Page<Map<String,Object>> findPrjProgPageBySql(Page<Map<String,Object>> page,
				Customer customer,UserContext uc);
		
		/**
		 * 软装未下单客户信息查询
		 * 
		 * @param page
		 * @param customer
		 * @return
		 */
		public Page<Map<String,Object>> findSoftNotAppQueryPageBySql(Page<Map<String,Object>> page, Customer customer);

	
	/**
	 * 客户列表接口
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_client(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);
	
	/**
	 * 客户列表接口(任务申请)
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_job(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);
	/**
	 * 客户列表接口(施工未报备)
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_report(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);
	
	/**
	 * 客户列表接口（不限部门权限）
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> getExecuteCustomerList(
			Page<Map<String, Object>> page, CustomerQueryEvt evts);

	/**
	 * 添加客户存储过程
	 * 
	 * @param customer
	 * @return
	 */
	public Result saveForProc(Customer customer);

	/**
	 * 修改客户存储过程
	 * 
	 * @param customer
	 * @return
	 */
	public Result updateForProc(Customer customer);

	/**
	 * 根据档案号获取客户
	 * 
	 * @param documentNo
	 * @return
	 */
	public Customer getByDocumentNo(String documentNo);

	/**
	 * 是否存在重复楼盘
	 * 
	 * @param address
	 * @param czybh
	 * @return
	 */
	public Map<String,Object> getByAddress(String address,String czybh);

	/**
	 * 客户信息查询
	 * 
	 * @param page
	 * @param customer
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_xxcx(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);

	/**
	 * 搜寻客户分页信息
	 * 
	 * @param page
	 * @param customer
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_code(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);

	/**
	 * 获取客户付款
	 * 
	 * @param code
	 * @return
	 */
	public Map<String, Object> getCustomerPayByCode(String code);

	/**
	 * 获取客户增减总金额
	 * 
	 * @param id
	 * @return
	 */
	public Double getCustomerZjzjeByCode(String id);

	/**
	 * 获取客户已付款金额
	 * 
	 * @param id
	 * @return
	 */
	public Double getCustomerHaspayByCode(String id);

	/**
	 * 获取客户材料需求
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustomerCode_itemReq(
			Page<Map<String, Object>> page, String code, String itemType1, String itemDescr,String itemType2);

	/**
	 * 获取客户材料变更
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustomerCode_itemChange(
			Page<Map<String, Object>> page, String code, String itemType1, String itemType2);
	
	/**
	 * 获取客户基础需求
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustomerCode_baseItemReq(
			Page<Map<String, Object>> page, String custCode, String fixAreaDescr);
	
	public Page<Map<String, Object>> findPageByCustomerCode_areaDescr(
			Page<Map<String, Object>> page, String custCode, String fixAreaDescr);
	
	/**
	 * 获取客户基础变更
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustomerCode_baseItemChange(
			Page<Map<String, Object>> page, String custCode);

	/**
	 * 获取客户领料
	 * 
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustomerCode_itemApp(
			Page<Map<String, Object>> page, String code, String itemType1,String refCustCode);

	/**获取付款详情明细
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getPayInfoDetailListByCode(String id);
	
	/**获取付款详情明细分页
	 * @param id
	 * @return
	 */
	public Page<Map<String, Object>> findPageByCustCode_payDetail(Page<Map<String, Object>> page,String custCode);
	
	/**获取干系人列表
	 * @param id
	 * @param uc
	 * @return
	 */
	public List<Map<String, Object>> getGxrListByCode(String id, UserContext uc);

	/**获取成本支出详情明细
	 * @param id
	 * @return
	 */
	public Result getCustomerCostByCode(String id);

	/**根据操作员编号获取工地
	 * @param id
	 * @return
	 */
	public List<Map<String, Object>> getByUserCustRight(String id,String status,String address);
	/**获取工地待验收客户列表
	 * @param id
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page<Map<String, Object>> getCheckCustomerByPage(Page page,String id,String status,String address);
	
	@SuppressWarnings("rawtypes")
	public Page<Map<String, Object>> getPrjCheckCustomerByPage(Page page,String id,String status,String address);

	/**
	 * 根据客户权限获取客户列表
	 * @param page
	 * @param customer
	 * @param uc
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_custRight(Page<Map<String, Object>> page, Customer customer,
			UserContext uc);
	/**
	 * 根据mobile1查询客户
	 */
	public Customer getCustomerByMobile1(String phone);
	
	/**
	 * 根据code查询客户
	 */
	public Map<String, Object> getCustomerByCode(String code);
	/**
	 * 根据code查询客户合同信息
	 */
	public Map<String, Object> getCustomerByCode_htxx(String code);
	/**
	 * 根据code查询客户工程信息
	 */
	public Map<String, Object> getCustomerByCode_gcxx(String code);
	/**
	 * 预扣客户列表
	 */
	public Page<Map<String, Object>> findPageBySql_ykCustomer(Page<Map<String, Object>> page, Customer customer);

	/**
	 * 预扣客户列表new
	 */
	public Page<Map<String, Object>> findPageBySql_ykCustomer_new(Page<Map<String, Object>> page, Customer customer);
	
	public Result deleteForProc(Customer customer);
	/**
	 * 巡检执行客户列表接口（不限部门权限）
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> getAllCustomerList(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);
	/**
	 	调用基础预算套餐存储过程填充数据
	 * 
	 * @param customer
	 * @return
	 */
	public void updateForJcysTcProc(Customer customer);

	public Page<Map<String,Object>> findItemConfirmPageBySql(Page<Map<String, Object>> page,
			Customer customer, UserContext uc);


	
	public void prjProgDeleteCustCode(String code);
	
	public Page<Map<String,Object>> getCustomerByConditions(Page<Map<String,Object>> page,GetCustomerEvt evt);
	
	//add by hc 来客统计 begin 2018/01/03
	public Page<Map<String,Object>> findPageBySqlTJFS(Page<Map<String,Object>> page,  Customer customer,String orderBy,String direction);
	//end by hc 来客统计 end 2018
	
	/**
	 * 根据客户编号获取客户类型的类型
	 * @param custCode
	 * @return
	 */
	public String getCustTypeTypeByCode(String custCode);
	
	public List<Map<String, Object>> getRollList();
	
	public boolean getIsDelivBuilder(String builderCode) ;
	
	/**
	 * 获取项目的地址类型
	 * @param builderCode
	 * @return
	 */
	public String getAddressType(String builderCode);

	public boolean getIsExistBuilderNum(String builderCode,String builderNum);
	
	public Page<Map<String, Object>> findPageBySql_gcwg(
			Page<Map<String, Object>> page, Customer customer);
	
	/**
	 * 根据code查询客户
	 */
	public Map<String, Object> getCustomerByCode_gcwg(String code);
	
	public Customer getChgData(Customer customer);
	
	public Result doGcwg_jz(Customer customer);

	public Result doGcwg_Khjs(Customer customer);

	public Map<String, Object> getShouldBanlance(String code);
	
	public List<Map<String, Object>> getHasZCReq(String code);

	public String isNeedPlanSendDate(String custCode);
	
	public String getIsDefaultStatic();
	
	public Map<String, Object> findMapByCode(String code);
	
	/**
	 * 分页查询施工合同信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageBySql_sghtxx(
			Page<Map<String, Object>> page, Customer customer, UserContext uc);
	
	public Double getJzyjjs(String custCode);
	/**
	 * 工程完工--强制结算
	 * @param customer
	 */
	public void doQzjs(Customer customer);
	/**
	 * 工程完工--强制结算退回
	 * @param customer
	 */
	public void doQzjsth(Customer customer);
	
	public String getZsgStatus(String custCode,String status);

	public String getJzNotify(String custCode,String status);
	
	public String getConBackNotify(String custCode,String status,String endCode,String m_umstatus);

	public String resignNotify(String custCode,String status,String endCode);

	public String getJzReturnNotify(String custCode,String status,String endCode);

	public Result doSghtxx_return(Customer customer); 

	public Result doSaveZsg(Customer customer); 

	public Result doWfProcSaveZsg(Customer customer); 

	public String getDesignPicStatus(String custCode);
	
	public String checkDesignFee(String custCode);

	public String getPosiDesignFee(String position ,String custType);
	
	public String getDesignFeeType(String custType,String payType);
	
	public Result doSaveJz(Customer customer); 
	
	public Result doJzReturn(Customer customer);
	
	public String getOldStatus(String custCode);
	
	public Result doReSign(Customer customer);

	public Page<Map<String, Object>> findPageBySql_viewResign(Page page,Customer customer);
	
	public Map<String , Object> getFourPay(String custCode,String payType);

	public Integer getCustConsDay(String custCode);
	
	//获取材料分类1统计
	public List<Map<String, Object>> getItemReqCount(String custCode);
	
	//获取主材软装材料分类统计
	public List<Map<String, Object>> getItemType12ReqCount(String custCode);
	
	//获取集成材料分类统计
	public List<Map<String, Object>> getJCReqCount(String custCode);
	
	//获取2级菜单材料需求
	public List<Map<String, Object>> getCustomerItemType12DetailNeeds(Customer customer);
	
	public Page<Map<String, Object>> getPrjReport(Page page,String custCode);

	public Page<Map<String, Object>> findItemSendListPage(Page page,String custCode,String itemType1);

	public Page<Map<String, Object>> findItemSendDetailListPage(Page page,String no);

	public Page<Map<String , Object>> getConstractPayPage(Page page,String custCode);
	
	public Result doUpdateContractFee(Customer customer);

	
	public List<Map<String,Object>> getBuildPassword(String code,int custWkPk);

	public boolean getMatchConDay(String custCode);
	
	public void updateCustAccountPk(String mobile1);
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemTypeByCustCode(
			Page<Map<String, Object>> page, String code);
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemType2ByCustCode(
			Page<Map<String, Object>> page, String code,String itemType1);
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemTypeChgByCustCode(
			Page<Map<String, Object>> page, String code);
	
	public Page<Map<String, Object>> findPageByCustomerCode_itemType2ChgByCustCode(
			Page<Map<String, Object>> page, String code,String itemType1);
	
	public Page<Map<String, Object>> findItemType1ListPage(
			Page<Map<String, Object>> page);
	/**
	 * 修改安装电梯，搬运楼层
	 * @author cjg
	 * @date 2019-4-20
	 * @param customer
	 */

	public Page<Map<String, Object>> findPageBySql_detailReport(
			Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findPageBySql_detailReportMX(
			Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findPageBySql_jcys(
			Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findPageBySql_jczj(
			Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findPageBySql_jczjDetail(
			Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> findPageBySql_jczfb(
			Page<Map<String, Object>> page, Customer customer);
	
	public Page<Map<String, Object>> getReqCtrlList(
			Page<Map<String, Object>> page, Customer customer);
	
	public Result doSaveReqCtrlPer(Customer customer);
	
	public void updateCarryFloor(Customer customer );

	public Page<Map<String, Object>> getTechPhotoUrlList(Page page,String custCode);
	
	public Page<Map<String, Object>> findPageBySql_modifyPhone(
			Page<Map<String, Object>> page, Customer customer);
	
	public String getConStatAndIsAddAll(String code);

	public Map<String, Object> getGiftDiscAmount(String custCode) ;

	public Map<String,Object> getContractInfo(String code);
	
	public Page<Map<String, Object>> getBaseCheckItemPlanList(Page<Map<String, Object>> page,String custCode,String workType12); 
	
	public boolean getExistBuilderNum(String builderCode);
	/**
	 * 风控基金统计
	 * @author	created by zb
	 * @date	2019-10-25--下午6:14:58
	 * @param id
	 * @return
	 */
	public Double getRiskFund(String id);
	/**
	 * 风控基金明细
	 * @author	created by zb
	 * @date	2019-10-28--下午5:37:46
	 * @param page
	 * @param customer
	 */
	public Page<Map<String, Object>> findRiskFundPageBySql(Page<Map<String, Object>> page,
			Customer customer);
	
	public String getPayeeCode(String code);
	/**
	 * 根据小区表，交房（tBuilder.DelivDate）超过1年，默认不能施工。1年之内默认可施工。
	 * @author	created by zb
	 * @date	2020-2-19--下午4:45:08
	 * @param customer
	 */
	public Customer isHoliConstruct(Customer customer);
	/**
	 * 是否需要跟单员
	 * @param code
	 * @return
	 */
	public Map<String,Object> isNeedAgainMan(String code);
	/**
	 * 是否有客户付款
	 * @param code
	 * @return
	 */
	public String hasPay(String code);
	
	public Map<String,Object> getWaterMarkInfo(String code);
	
	/**
	 * 查询楼盘所属工程大区
	 * @param code
	 * @return
	 */
	public Map<String, Object> getPrjRegion(String code);

	/**
	 * 量房
	 * 
	 * @param request
	 * @param response
	 * @param customer
	 * @param userContext
	 * @return
	 * @author 张海洋
	 */
    public Result doMeasure(HttpServletRequest request,
            HttpServletResponse response, Customer customer, UserContext userContext);
    
    public List<Map<String, Object>> getCustNetCnlList();
    
    
    public Map<String, Object> getMaxDiscByCustCode(String code);
    
    public List<Map<String, Object>> getCustNetCnlListBySource(String source);
    
    Result doUpdateBusinessMan(Customer customer, String businessMan, String againMan, UserContext userContext);
    
	public Result doWfCustReSign(Customer customer);

	public String getEndCodeByCustCode(String custCode);
	
	public String checkAddress(Customer customer, UserContext userContext);
	
	public String getContractStatus(Customer customer);
	
	public Map<String, Object> getPerfEstimate(String custCode, String type, String empCode);
	
	/**
	 * 签单预估--木作提成明细
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_basePersonalEstimate(Page<Map<String,Object>> page, Customer customer);
	
	/**
	 * 签单预估--主材提成明细
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_zcEstimate(Page<Map<String,Object>> page, Customer customer);
	
	/**
	 * 签单预估--软装提成明细
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_rzEstimate(Page<Map<String,Object>> page, Customer customer);
	
	/**
	 * 签单预估--集成提成明细
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql_jcEstimate(Page<Map<String,Object>> page, Customer customer);
	
	/**
	 * 
	 * @param page
	 * @param commiCal
	 * @return
	 */
	public boolean isSignDateAfterNewCommiDate(String custCode);
	
}
