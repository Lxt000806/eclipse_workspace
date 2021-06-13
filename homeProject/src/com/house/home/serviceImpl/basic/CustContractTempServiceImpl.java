package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.CustContractTempDao;
import com.house.home.entity.basic.CustContractTemp;
import com.house.home.service.basic.CustContractTempService;

@SuppressWarnings("serial")
@Service
public class CustContractTempServiceImpl extends BaseServiceImpl implements CustContractTempService {

	@Autowired
	private CustContractTempDao custContractTempDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustContractTemp custContractTemp){
		return custContractTempDao.findPageBySql(page, custContractTemp);
	}

	@Override
	public Result doSaveProc(CustContractTemp custContractTemp) {
		return custContractTempDao.doSaveProc(custContractTemp);
	}

	@Override
	public Page<Map<String, Object>> goDetailJqGrid(Page<Map<String, Object>> page, CustContractTemp custContractTemp) {
		return custContractTempDao.goDetailJqGrid(page, custContractTemp);
	}

	@Override
	public Map<String, Object> getCustContractTempFileName(Integer pk) {
		return custContractTempDao.getCustContractTempFileName(pk);
	}

}
