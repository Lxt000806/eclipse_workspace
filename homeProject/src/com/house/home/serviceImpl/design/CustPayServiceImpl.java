package com.house.home.serviceImpl.design;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.design.CustPayDao;
import com.house.home.entity.design.CustPay;
import com.house.home.entity.design.Customer;
import com.house.home.service.design.CustPayService;

@SuppressWarnings("serial")
@Service
public class CustPayServiceImpl extends BaseServiceImpl implements CustPayService {

	@Autowired
	private CustPayDao custPayDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Customer customer){
		return custPayDao.findPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findProcListJqGrid(
			Page<Map<String, Object>> page, CustPay custPay) {

		return custPayDao.findProcListJqGrid(page, custPay);
	}

	@Override
	public boolean hasCustPay(String custCode) {
		return custPayDao.hasCustPay(custCode);
	}

	@Override
	public Page<Map<String, Object>> findChgInfoPageBySql(Page<Map<String, Object>> page, Customer customer) {
		return custPayDao.findChgInfoPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findChgAppPageBySql(Page<Map<String, Object>> page, Customer customer) {
		return custPayDao.findChgAppPageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPayInfoPageBySql(Page<Map<String, Object>> page, Customer customer) {
		return custPayDao.findPayInfoPageBySql(page, customer);
	}

	@Override
	public List<Map<String, Object>> findActAndPosByAuthority(int type,String pCode, UserContext uc) {
		List<Map<String,Object>> resultList = Lists.newArrayList();
		Map<String,Object> param = new HashMap<String,Object>();
		if(type == 1){
			String itemRight="";
			for(String str:uc.getItemRight().trim().split(",")){
				itemRight+="'"+str+"',";			
			}
			param.put("pCode", itemRight.substring(0, itemRight.length()-1));
			resultList = this.custPayDao.findRcvAct(param);
		}else if(type == 2){//商品类型2
			param.put("pCode", pCode);
			resultList = this.custPayDao.findPos(param);
		}
		return resultList;
	}

	@Override
	public List<Map<String, Object>> findPayNo(String payNo,String pk) {
		return custPayDao.findPayNo(payNo,pk);
	}

	@Override
	public List<Map<String, Object>> findBankPos(String code) {
		return custPayDao.findBankPos(code);
	}

	@Override
	public void updatePayPlan(Customer customer) {
		custPayDao.updatePayPlan(customer);
	}

	@Override
	public List<Map<String, Object>> findDesignFeeType(Customer customer) {
		return custPayDao.findDesignFeeType(customer);
	}

	@Override
	public List<Map<String, Object>> findListBySql(Customer customer) {
		return custPayDao.findListBySql(customer);
	}

	@Override
	public Result doUpdateProc(Customer customer) {
		return custPayDao.doUpdateProc(customer);
	}

	@Override
	public List<Map<String, Object>> findCustStatus(Customer customer) {
		return custPayDao.findCustStatus(customer);
	}

	@Override
	public void updateRepairCard(Customer customer) {
		custPayDao.updateRepairCard(customer);
	}

	@Override
	public Page<Map<String, Object>> findDetailQueryBySql(
			Page<Map<String, Object>> page, CustPay custPay) {
		return custPayDao.findDetailQueryBySql(page, custPay);
	}

	@Override
	public Boolean doIsPubReturnSave(String isPubReturn, String custCode) {
		return custPayDao.doIsPubReturnSave(isPubReturn, custCode);
	}

	@Override
	public Page<Map<String, Object>> findChgInfoOrderDatePageBySql(
			Page<Map<String, Object>> page, Customer customer) {
		return custPayDao.findChgInfoOrderDatePageBySql(page, customer);
	}

	@Override
	public Page<Map<String, Object>> findPageByQPrintSql(Page<Map<String, Object>> page, Customer customer) {
		return custPayDao.findPageByQPrintSql(page, customer);
	}
	
	@Override
	public double getPayDesignFee(String custCode, String type) {
		return custPayDao.getPayDesignFee(custCode, type);
	}

	@Override
	public String getPayTimesByCustCode(String custCode) {

		return custPayDao.getPayTimesByCustCode(custCode);
	}

	@Override
	public List<Map<String, Object>> getPayInfo(CustPay custPay) {

		return custPayDao.getPayInfo(custPay);
	}
	
	@Override
	public Result doSaveBatch(CustPay custPay) {
		return custPayDao.doSaveBatch(custPay);
	}

	@Override
	public Page<Map<String, Object>> findPayBillQueryBySql(
			Page<Map<String, Object>> page, CustPay custPay) {
		return custPayDao.findPayBillQueryBySql(page, custPay);
	}

	@Override
	public Double getProcConfirmAmount(String no) {
		return custPayDao.getProcConfirmAmount(no);
	}
	
	
}
