package com.house.home.serviceImpl.workflow;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.workflow.ActCommentDao;
import com.house.home.entity.workflow.ActComment;
import com.house.home.service.workflow.ActCommentService;

@SuppressWarnings("serial")
@Service
public class ActCommentServiceImpl extends BaseServiceImpl implements ActCommentService {

	@Autowired
	private ActCommentDao actCommentDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ActComment actComment){
		return actCommentDao.findPageBySql(page, actComment);
	}

}
