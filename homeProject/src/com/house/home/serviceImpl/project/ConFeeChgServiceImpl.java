package com.house.home.serviceImpl.project;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.framework.web.login.UserContext;
import com.house.home.dao.project.ConFeeChgDao;
import com.house.home.entity.project.ConFeeChg;
import com.house.home.service.project.ConFeeChgService;

@SuppressWarnings("serial")
@Service
public class ConFeeChgServiceImpl extends BaseServiceImpl implements ConFeeChgService {

	@Autowired
	private ConFeeChgDao conFeeChgDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ConFeeChg conFeeChg,UserContext uc){
		return conFeeChgDao.findPageBySql(page, conFeeChg,uc);
	}

	@Override
	public Page<Map<String, Object>> findPageByCustCode(
			Page<Map<String, Object>> page, String custCode) {
		return conFeeChgDao.findPageByCustCode(page,custCode);
	}

	@Override
	public List<Map<String, Object>> checkPerformance(ConFeeChg conFeeChg) {
		return conFeeChgDao.checkPerformance(conFeeChg);
	}

	@Override
	public List<Map<String, Object>> checkSavePerformance(ConFeeChg conFeeChg) {
		return conFeeChgDao.checkSavePerformance(conFeeChg);
	}

	@Override
	public void doPerformance(ConFeeChg conFeeChg) {
		conFeeChgDao.doPerformance(conFeeChg);
	}

	@Override
	public Result doSaveProc(ConFeeChg conFeeChg) {
		return conFeeChgDao.doSaveProc(conFeeChg);
	}

}
