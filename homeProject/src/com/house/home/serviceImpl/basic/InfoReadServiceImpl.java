package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.InfoReadDao;
import com.house.home.entity.basic.InfoRead;
import com.house.home.service.basic.InfoReadService;

@SuppressWarnings("serial")
@Service
public class InfoReadServiceImpl extends BaseServiceImpl implements InfoReadService {

	@Autowired
	private InfoReadDao infoReadDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, InfoRead infoRead){
		return infoReadDao.findPageBySql(page, infoRead);
	}

	@Override
	public Result updateForProc(InfoRead infoRead) {
		return infoReadDao.updateForProc(infoRead);
	}

}
