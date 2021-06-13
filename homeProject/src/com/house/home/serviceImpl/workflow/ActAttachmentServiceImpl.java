package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActAttachmentDao;
import com.house.home.entity.workflow.ActAttachment;
import com.house.home.service.workflow.ActAttachmentService;

@SuppressWarnings("serial")
@Service
public class ActAttachmentServiceImpl extends BaseServiceImpl implements ActAttachmentService {

	@Autowired
	private ActAttachmentDao actAttachmentDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActAttachment actAttachment){
		return actAttachmentDao.findPageBySql(page, actAttachment);
	}

}
