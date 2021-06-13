package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.InformationDao;
import com.house.home.entity.basic.Information;
import com.house.home.service.basic.InformationService;

@SuppressWarnings("serial")
@Service
public class InformationServiceImpl extends BaseServiceImpl implements InformationService {

	@Autowired
	private InformationDao informationDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Information information){
		return informationDao.findPageBySql(page, information);
	}
	
	public Page<Map<String,Object>> findPageBySqlForInfo(Page<Map<String,Object>> page, Information information){
		return informationDao.findPageBySqlForInfo(page, information);
	}

	@Override
	public Long getCount(Information information) {
		return informationDao.getCount(information);
	}

	@Override
	public Map<String, Object> getByNumber(String number) {
		return informationDao.getByNumber(number);
	}

	public Page<Map<String,Object>> findInfoReadPageBySql(Page<Map<String,Object>> page, Information information){
		return informationDao.findInfoReadPageBySql(page, information);
	}
	
	public Page<Map<String,Object>> findInfoAttachPageBySql(Page<Map<String,Object>> page, Information information){
		return informationDao.findInfoAttachPageBySql(page, information);
	}
	
	
	public List<Map<String,Object>> getChildrenNodes(String higherDept){
		return informationDao.getChildrenNodes(higherDept);
	}

	@Override
	public Result doSave(Information information) {
		return informationDao.doSave(information);
	}

	@Override
	public List<Map<String, Object>> getDepEmpNodes() {
		return informationDao.getDepEmpNodes();
	}
	
	public List<Map<String,Object>> getReceiveNodes(String infoNum){
		return informationDao.getReceiveNodes(infoNum);
	}

	public String getNeedSendInfomation(String czybm){
		return informationDao.getNeedSendInfomation(czybm);
	}
}
