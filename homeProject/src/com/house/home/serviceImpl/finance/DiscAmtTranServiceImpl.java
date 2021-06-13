package com.house.home.serviceImpl.finance;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.DiscAmtTranDao;
import com.house.home.entity.finance.DiscAmtTran;
import com.house.home.service.finance.DiscAmtTranService;

@SuppressWarnings("serial")
@Service 
public class DiscAmtTranServiceImpl extends BaseServiceImpl implements DiscAmtTranService {
	@Autowired
	private  DiscAmtTranDao discAmtTranDao;

	@Override
	public Page<Map<String, Object>> findDiscAmtTranPageBySql(
			Page<Map<String, Object>> page, DiscAmtTran discAmtTran) {
		return discAmtTranDao.findDiscAmtTranPageBySql(page,discAmtTran);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DiscAmtTran discAmtTran) {
		return discAmtTranDao.findPageBySql(page, discAmtTran);
	}

	@Override
	public void doSave(DiscAmtTran discAmtTran) {
		discAmtTran.setLastUpdate(new Date());
		discAmtTran.setExpired("F");
		discAmtTran.setActionLog("ADD");
		this.save(discAmtTran);
	}

	@Override
	public double getLpExpense(String custCode) {
		return discAmtTranDao.getLpExpense(custCode);
	}
	
}
