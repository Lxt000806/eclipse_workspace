package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.CustLoanDao;
import com.house.home.entity.design.CustLoan;
import com.house.home.service.design.CustLoanService;

@SuppressWarnings("serial")
@Service
public class CustLoanServiceImpl extends BaseServiceImpl implements CustLoanService {

	@Autowired
	private CustLoanDao custLoanDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustLoan custLoan){
		return custLoanDao.findPageBySql(page, custLoan);
	}

	@Override
	public Result doSaveBatch(CustLoan custLoan) {
		return custLoanDao.doSaveBatch(custLoan);
	}

	@Override
	public boolean isExistCustCode(CustLoan custLoan) {
		// TODO Auto-generated method stub
		return custLoanDao.isExistCustCode(custLoan);
	}

}
