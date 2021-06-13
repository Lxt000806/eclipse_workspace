package com.house.home.service.finance;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.finance.ReturnPay;

public interface ReturnPayService extends BaseService{
	
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ReturnPay returnPay);
	
	public Page<Map<String,Object>> findDetailByNo(Page<Map<String,Object>> page, ReturnPay returnPay);
	
	public Result doSave(ReturnPay returnPay);
	
	public Result doConfirmPass(ReturnPay returnPay);
	
	public Result doConfirmCancel(ReturnPay returnPay);
	
	public ReturnPay getByNo(String no);
	
	public List<Map<String, Object>> findReturnCustCode(ReturnPay returnPay);

    public Page<Map<String, Object>> settleRefundsGrid(Page<Map<String, Object>> page, ReturnPay returnPay);

    public void doBatchCheck(HttpServletRequest request, HttpServletResponse response,
            ReturnPay returnPay);

}
