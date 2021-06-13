package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActTaskinstDao;
import com.house.home.entity.workflow.ActTaskinst;
import com.house.home.service.workflow.ActTaskinstService;

@SuppressWarnings("serial")
@Service
public class ActTaskinstServiceImpl extends BaseServiceImpl implements ActTaskinstService {

	@Autowired
	private ActTaskinstDao actTaskinstDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActTaskinst actTaskinst){
		return actTaskinstDao.findPageBySql(page, actTaskinst);
	}

	@Override
	public Page<Map<String, Object>> findByProcInstId(Page<Map<String,Object>> page, String procInstId) {
		return actTaskinstDao.findByProcInstId(page, procInstId);
	}

}
