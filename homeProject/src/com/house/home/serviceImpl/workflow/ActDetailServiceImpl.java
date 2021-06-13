package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActDetailDao;
import com.house.home.entity.workflow.ActDetail;
import com.house.home.service.workflow.ActDetailService;

@SuppressWarnings("serial")
@Service
public class ActDetailServiceImpl extends BaseServiceImpl implements ActDetailService {

	@Autowired
	private ActDetailDao actDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActDetail actDetail){
		return actDetailDao.findPageBySql(page, actDetail);
	}

}
