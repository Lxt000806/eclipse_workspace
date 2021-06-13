package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.SupplierPay;

public interface SupplierPayService extends BaseService{

	/**
	 * 供应商付款分页详细查询
	 */
	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, SupplierPay supplierPay);

	/**
	 * 供应商付款明细分页查询 
	 */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, SupplierPay supplierPay);

	/**
	 * 根据付款总金额分配本次付款
	 */
	public List<Map<String, Object>> doSetPaidAmount(SupplierPay supplierPay);

	/**
	 * 根据结算单号获取供应商和预付款余额
	 */
	public Map<String, Object> getSplInfo(SupplierPay supplierPay);

	/**
	 * 付款金额是否大于应付余额 保存时用到_SumPaidAmount
	 */
	public Map<String, Object> getSumPaidAmount(SupplierPay supplierPay);
	
	/**
	 * SumRemainAmount
	 */
	public Map<String, Object> getSumRemainAmount(SupplierPay supplierPay);

	/**
	 * 付款金额是否大于应付余额 审核通过时用到_SumPaidAmount
	 */
	public Map<String, Object> getSumPaidAmount2(SupplierPay supplierPay);

	/**
	 * 走pGysfkgl存储过程
	 */
	public Result doSave(SupplierPay supplierPay);

}
