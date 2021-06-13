package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActActinstDao;
import com.house.home.entity.workflow.ActActinst;
import com.house.home.service.workflow.ActActinstService;

@SuppressWarnings("serial")
@Service
public class ActActinstServiceImpl extends BaseServiceImpl implements ActActinstService {

	@Autowired
	private ActActinstDao actActinstDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActActinst actActinst){
		return actActinstDao.findPageBySql(page, actActinst);
	}

}
