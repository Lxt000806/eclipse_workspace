package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.CustIntProgDao;
import com.house.home.entity.project.CustIntProg;
import com.house.home.service.project.CustIntProgService;

@SuppressWarnings("serial")
@Service
public class CustIntProgServiceImpl extends BaseServiceImpl implements CustIntProgService {

	@Autowired
	private CustIntProgDao custIntProgDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, CustIntProg custIntProg){
		return custIntProgDao.findPageBySql(page, custIntProg);
	}

	@Override
	public Map<String, Object> getCustIntProgDetail(String custCode) {
		return custIntProgDao.getCustIntProgDetail(custCode);
	}

	@Override
	public Map<String, Object> findSenddaysByMaterial(String material,
			String itemType12) {
		return custIntProgDao.findSenddaysByMaterial(material, itemType12);
	}

	@Override
	public void doDelayAdd(CustIntProg custIntProg) {
		custIntProgDao.doDelayAdd(custIntProg);
	}

	@Override
	public List<Map<String, Object>> checkRegistered(String custCode) {
		return custIntProgDao.checkRegistered(custCode);
	}

	@Override
	public List<Map<String, Object>> checkDelayed(String custCode) {
		return custIntProgDao.checkDelayed(custCode);
	}

	@Override
	public Result doSaveProc(CustIntProg custIntProg) {
		return custIntProgDao.doSaveProc(custIntProg);
	}

}
