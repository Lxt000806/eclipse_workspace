package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BankPosDao;
import com.house.home.entity.basic.BankPos;
import com.house.home.service.basic.BankPosService;

@SuppressWarnings("serial")
@Service
public class BankPosServiceImpl extends BaseServiceImpl implements BankPosService {

	@Autowired
	private BankPosDao bankPosDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BankPos bankPos){
		return bankPosDao.findPageBySql(page, bankPos);
	}

	@Override
	public void doUpdate(BankPos bankPos) {
		bankPosDao.doUpdate(bankPos);
	}

	@Override
	public boolean checkExsist(BankPos bankPos) {
		return bankPosDao.checkExsist(bankPos);
	}

	@Override
	public Map<String, Object> getBankPosDetail(String code) {
		return bankPosDao.getBankPosDetail(code);
	}

}
