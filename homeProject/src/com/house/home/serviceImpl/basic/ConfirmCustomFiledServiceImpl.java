package com.house.home.serviceImpl.basic;

import com.house.framework.commons.orm.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.project.ConfirmCustomFiledDao;
import com.house.home.service.basic.ConfirmCustomFiledService;

import com.house.framework.commons.orm.Page;

import java.util.List;
import java.util.Map;
import com.house.home.entity.basic.ConfirmCustomFiled;
@SuppressWarnings("serial")
@Service 
public class ConfirmCustomFiledServiceImpl extends BaseServiceImpl implements ConfirmCustomFiledService {
	@Autowired
	private  ConfirmCustomFiledDao confirmCustomFiledDao;
	
	@Override	public Page<Map<String, Object>> findPageBySql(Page<Map<String, Object>> page, ConfirmCustomFiled confirmCustomFiled){
	
		return confirmCustomFiledDao.findPageBySql(page, confirmCustomFiled);
	}
	@Override
	public List<Map<String, Object>> checkDescrExists(String descr, String code) {

		return confirmCustomFiledDao.checkDescrExists(descr, code);
	}
	
	
}
