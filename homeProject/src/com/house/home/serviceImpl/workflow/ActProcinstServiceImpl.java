package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActProcinstDao;
import com.house.home.entity.workflow.ActProcinst;
import com.house.home.service.workflow.ActProcinstService;

@SuppressWarnings("serial")
@Service
public class ActProcinstServiceImpl extends BaseServiceImpl implements ActProcinstService {

	@Autowired
	private ActProcinstDao actProcinstDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActProcinst actProcinst){
		return actProcinstDao.findPageBySql(page, actProcinst);
	}

}
