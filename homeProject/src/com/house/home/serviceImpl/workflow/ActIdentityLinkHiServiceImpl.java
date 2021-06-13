package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActIdentityLinkHiDao;
import com.house.home.entity.workflow.ActIdentityLinkHi;
import com.house.home.service.workflow.ActIdentityLinkHiService;

@SuppressWarnings("serial")
@Service
public class ActIdentityLinkHiServiceImpl extends BaseServiceImpl implements ActIdentityLinkHiService {

	@Autowired
	private ActIdentityLinkHiDao actIdentityLinkHiDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActIdentityLinkHi actIdentityLinkHi){
		return actIdentityLinkHiDao.findPageBySql(page, actIdentityLinkHi);
	}

}
