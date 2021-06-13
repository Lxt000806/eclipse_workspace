package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;

public interface CustPayService extends BaseService {

	/**CustPay分页信息
	 * @param page
	 * @param custPay
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer);
	
	public Page<Map<String,Object>> findProcListJqGrid(Page<Map<String,Object>> page, CustPay custPay);
	
	/**
	 * 是否客户付款
	 * @param custCode
	 * @return
	 */
	public boolean hasCustPay(String custCode);
	/**
	 * 增减信息分页信息
	 * 
	 * @param page
	 * @param custPay
	 * @return
	 */
	public Page<Map<String, Object>> findChgInfoPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 增减申请分页信息
	 * 
	 * @param page
	 * @param custPay
	 * @return
	 */
	public Page<Map<String, Object>> findChgAppPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 付款信息分页信息
	 * 
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPayInfoPageBySql(Page<Map<String, Object>> page, Customer customer);
	/**
	 * 收款账号，pos机二级联动
	 * @param type
	 * @param pCode
	 * @param uc
	 * @return
	 */
	public List<Map<String, Object>> findActAndPosByAuthority(int type,String pCode,UserContext uc);
	/**
	 * 查收款单号
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findPayNo(String payNo,String pk);
	/**
	 * 查bankPos相关
	 * 
	 * @param param
	 * @return
	 */
	public List<Map<String, Object>> findBankPos(String code);
	/**
	 * 修改付款计划
	 * @param customer
	 * @return
	 */
	public void updatePayPlan(Customer customer);
	/**
	 * 查设计费类型
	 * 
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> findDesignFeeType(Customer customer);
	/**
	 * 查custpay列表
	 * 
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> findListBySql(Customer customer);
	/**
	 * 修改客户资料
	 * 
	 * @param customer
	 * @return
	 */
	public Result doUpdateProc(Customer customer);
	/**
	 * 查客户状态
	 * 
	 * @param customer
	 * @return
	 */
	public List<Map<String, Object>> findCustStatus(Customer customer);
	/**
	 * 修改保修卡
	 * 
	 * @param customer
	 * @return
	 */
	public void updateRepairCard(Customer customer);
	/**
	 * 客户付款明细查询
	 * 
	 * @param page
	 * @param custPay
	 * @return
	 */
	public Page<Map<String,Object>> findDetailQueryBySql(Page<Map<String,Object>> page, CustPay custPay);
	/**
	 * 保存对公退款
	 * @author	created by zb
	 * @date	2018-12-7--下午5:24:10
	 * @param isPubReturn
	 * @param custCode 
	 * @return
	 */
	public Boolean doIsPubReturnSave(String isPubReturn, String custCode);
	/**
	 * 客户增减信息按照时间排序（工程进度拖延分析查看用）
	 * @author	created by zb
	 * @date	2019-11-8--下午3:56:54
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findChgInfoOrderDatePageBySql(Page<Map<String, Object>> page,
			Customer customer);
	/**
	 * 客户付款批量打印JqGrid
	 * @author cjg
	 * @date 2020-1-2
	 * @param page
	 * @param customer
	 * @return
	 */
	public Page<Map<String, Object>> findPageByQPrintSql(Page<Map<String, Object>> page, Customer customer);
	
	/**
	 * 获取客户已付款金额
	 * @param custCode 
	 * @return type 1设计定金，2工程款
	 */
	

	/**
	 * 获取付款次数
	 */
	public String getPayTimesByCustCode(String custCode);
	
	public List<Map<String,Object>> getPayInfo(CustPay custPay);
	
	public double getPayDesignFee(String custCode, String type);
	/**
	 * 付款信息导入调过程
	 * @param custTypeItem
	 * @return
	 */
	public Result doSaveBatch (CustPay custPay);
	
	/**
	 * 客户付款记录查询
	 * 
	 * @param page
	 * @param custPay
	 * @return
	 */
	public Page<Map<String,Object>> findPayBillQueryBySql(Page<Map<String,Object>> page, CustPay custPay);
	
	public Double getProcConfirmAmount(String no);
	
}
