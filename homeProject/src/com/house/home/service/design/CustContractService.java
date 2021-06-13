package com.house.home.service.design;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.PersonMessage;
import com.house.home.entity.design.CustContract;
import com.house.home.entity.design.CustContractValue;
import com.house.home.entity.design.CustProfit;
import com.house.home.entity.design.Customer;

public interface CustContractService extends BaseService {

	/**CustContract分页信息
	 * @param page
	 * @param custContract
	 * @return
	 */
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContract custContract);
	
	/**
	 * 获取跳转到创建合同所需要的默认信息（不存在历史合同记录）
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getConInfo(CustContract custContract);
	
	/**
	 * 创建合同前置条件：未到公司、已到公司、订单状态，不存在已申请、已审核、已签约状态的合同记录
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String,Object>> hasCon(CustContract custContract);
	
	/**
	 * 获取跳转到创建合同所需要的默认信息（存在历史合同记录）
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getHisConInfo(CustContract custContract);
	
	/**
	 * 创建合同保存
	 * 
	 * @param custContract
	 * @return
	 */
	public Result doCreate(CustContract custContract,Customer customer,CustProfit...custProfits);
	
	/**
	 * 获取合同编号
	 * 
	 * @param custType
	 * @return
	 */
	public Result getConNo(String custType);
	
	/**
	 * 获取合同模板和版本号
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getContractTemp(String custType,String type,String builderCode);
	
	/**
	 * 获取重签表原客户类型
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> getOldCustType(String custCode);
	
	/**
	 * 提交审核
	 * 
	 * @param custContract
	 * @return
	 */
	public Result doSubmit(CustContract custContract,Customer customer,String postData);
	
	/**
	 * 审核通过
	 * 
	 * @param custContract
	 * @return
	 */
	public Result doConfirmPass(CustContract custContract,PersonMessage personMessage);
	
	/**
	 * 审核取消
	 * 
	 * @param custContract
	 * @return
	 */
	public void doConfirmCancel(CustContract custContract,PersonMessage personMessage);
	
	/**
	 * 取消合同
	 * 
	 * @param custContract
	 * @return
	 */
	public Result doCancel(CustContract custContract);
	
	/**
	 * 签署结果回调执行操作
	 * 
	 * @param map
	 * @return
	 */
	public void eSignCallBack(Map<String, Object> map);
	
	/**
	 * 获取合同模板类型
	 * 
	 * @param custContract
	 * @return
	 */
	public String getConTypeDescr(String type);
	
	/**
	 * 最近一条记录是否为已申请、已审核、已签约
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String,Object>> latelyCon(CustContract custContract);
	
	/**
	 * 编辑合同保存
	 * 
	 * @param custContract
	 * @return
	 */
	public void doUpdate(CustContract custContract,Customer customer,CustProfit...custProfits);
	
	/**
	 * 生成合同文件
	 * @param custContract
	 * @param customer
	 * @param postData
	 * @param isUpload
	 * @return
	 */
	public Result createDoc(CustContract custContract, Customer customer,String postData,boolean isUpload);
	
	/**
	 * 是否包含主材，集成，软装预算
	 * @param custCode
	 * @return
	 */
	public Map<String, Object> hasItemPlan(String custCode);
	
	/**
	 * 获取历史合同模板和版本号
	 * 
	 * @return
	 */
	public Map<String,Object> getHisContractTemp(Integer conPk,Integer conTempPk ,String type);
	
	/**
	 * 最近一条已签约记录
	 * 
	 * @param custContract
	 * @return
	 */
	public Map<String,Object> latelySignedCon(CustContract custContract);
	
	/**
	 * 是否有草稿状态的合同
	 * 
	 * @param custContract
	 * @return
	 */
	public List<Map<String, Object>> hasDraftCon(CustContract custContract);
	
	/**
	 * 发送签署
	 * 
	 * @param custContract
	 * @return
	 */
	public Result doSendDesign(CustContract custContract,Customer customer);
	
	/**
	 * 获取默认报表
	 * @param customer
	 * @return
	 */
	public Map<String, Object> getDefaultReport(Customer customer);
}
