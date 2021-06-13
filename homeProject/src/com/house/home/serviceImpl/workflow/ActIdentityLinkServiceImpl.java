package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActIdentityLinkDao;
import com.house.home.entity.workflow.ActIdentityLink;
import com.house.home.service.workflow.ActIdentityLinkService;

@SuppressWarnings("serial")
@Service
public class ActIdentityLinkServiceImpl extends BaseServiceImpl implements ActIdentityLinkService {

	@Autowired
	private ActIdentityLinkDao actIdentityLinkDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActIdentityLink actIdentityLink){
		return actIdentityLinkDao.findPageBySql(page, actIdentityLink);
	}

}
