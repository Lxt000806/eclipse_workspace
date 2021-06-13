package com.house.home.serviceImpl.oa;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.oa.LeaveDao;
import com.house.home.entity.oa.Leave;
import com.house.home.service.oa.LeaveService;

@SuppressWarnings("serial")
@Service
public class LeaveServiceImpl extends BaseServiceImpl implements LeaveService {

	@Autowired
	private LeaveDao leaveDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, Leave leave){
		return leaveDao.findPageBySql(page, leave);
	}

	@Override
	public Leave getByProcessInstanceId(String id) {
		return leaveDao.getByProcessInstanceId(id);
	}

}
