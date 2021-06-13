package com.house.home.service.finance;

import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.PayCheckOut;

public interface PayCheckOutService extends BaseService{
    
   /**
    * 显示主界面数据和查询SQL语句
    * @param page
    * @param payCheckOut
    * @return
    */
    public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page,PayCheckOut payCheckOut);
    
    /**
     *  多表查询客户付款单信息 
     * @param page
     * @param custPay
     * @param payCheckOut
     * @param customer
     * @return
     */
    public Page<Map<String,Object>> findCustPayPageBySql(Page<Map<String, Object>> page, 
            CustPay custPay, PayCheckOut payCheckOut, Customer customer);

    /**
     * 保存收入记账订单
     * @param payCheckOut
     * @return
     */
    public Result savePayCheckOut(PayCheckOut payCheckOut);
	/**
	 * 显示明细表
	 * 
	 * @param page
	 * @param payCheckOut
	 * @return
	 */
	public Page<Map<String, Object>> findDetailPageBySql(Page<Map<String, Object>> page, PayCheckOut payCheckOut); 
	/**
	 * 主表map
	 * 
	 * @param page
	 * @param payCheckOut
	 * @return
	 */
	public Map<String, Object> findMapBySql(PayCheckOut payCheckOut);
	/**
	 * 审核、审核取消，反审核
	 * @param payCheckOut
	 * @return
	 */
	public Result checkPayCheckOut(PayCheckOut payCheckOut);
}
