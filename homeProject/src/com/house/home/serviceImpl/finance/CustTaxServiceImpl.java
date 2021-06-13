package com.house.home.serviceImpl.finance;

import java.util.List;
import java.util.Map;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.finance.CustTaxDao;
import com.house.home.entity.design.Customer;
import com.house.home.entity.finance.CustTax;
import com.house.home.service.finance.CustTaxService;

@SuppressWarnings("serial")
@Service 
public class CustTaxServiceImpl extends BaseServiceImpl implements CustTaxService {
	@Autowired
	private  CustTaxDao custTaxDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, CustTax custTax, Customer customer) {
		return custTaxDao.findPageBySql(page, custTax, customer);
	}

	@Override
	public Result doSave(CustTax custTax) {
		return custTaxDao.doSave(custTax);
	}

	@Override
	public Page<Map<String, Object>> findDetailByCode(
			Page<Map<String, Object>> page, CustTax custTax) {
		return custTaxDao.findDetailByCode(page,custTax);
	}

	@Override
	public Page<Map<String, Object>> findInvoicePageBySql(
			Page<Map<String, Object>> page, CustTax custTax, Customer customer) {
		return custTaxDao.findInvoicePageBySql(page, custTax, customer);
	}

	@Override
	public Page<Map<String, Object>> findCustPayPageBySql(
			Page<Map<String, Object>> page, String custCode) {
		return custTaxDao.findCustPayPageBySql(page, custCode);
	}

	@Override
	public Page<Map<String, Object>> findLaborPageBySql(
			Page<Map<String, Object>> page, CustTax custTax) {
		return custTaxDao.findLaborPageBySql(page, custTax);
	}

	@Override
	public Result doCustInvoice(CustTax custTax) {
		return custTaxDao.doCustInvoice(custTax);
	}

	@Override
	public Result doCustLaborInvoice(CustTax custTax) {
		return custTaxDao.doCustLaborInvoice(custTax);
	}

	@Override
	public Page<Map<String, Object>> goLaborCtrlListJqGrid(Page<Map<String, Object>> page, Customer customer) {
		return custTaxDao.goLaborCtrlListJqGrid(page, customer);
	}

    @Override
    public List<Map<String, Object>> findLaborCompanyList() {
        return custTaxDao.findLaborCompanyList();
    }

}
