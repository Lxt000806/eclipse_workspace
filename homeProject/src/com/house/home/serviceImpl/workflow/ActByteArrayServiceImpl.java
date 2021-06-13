package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActByteArrayDao;
import com.house.home.entity.workflow.ActByteArray;
import com.house.home.service.workflow.ActByteArrayService;

@SuppressWarnings("serial")
@Service
public class ActByteArrayServiceImpl extends BaseServiceImpl implements ActByteArrayService {

	@Autowired
	private ActByteArrayDao actByteArrayDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActByteArray actByteArray){
		return actByteArrayDao.findPageBySql(page, actByteArray);
	}

}
