package com.house.home.serviceImpl.oa;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.oa.UserLeaderDao;
import com.house.home.entity.oa.UserLeader;
import com.house.home.service.oa.UserLeaderService;

@SuppressWarnings("serial")
@Service
public class UserLeaderServiceImpl extends BaseServiceImpl implements UserLeaderService {

	@Autowired
	private UserLeaderDao userLeaderDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, UserLeader userLeader){
		return userLeaderDao.findPageBySql(page, userLeader);
	}

	@Override
	public List<Map<String, Object>> findByLeaderId(String czybh) {
		return userLeaderDao.findByLeaderId(czybh);
	}

	@Override
	public UserLeader getByUserIdAndLeaderId(String userId, String leaderId) {
		return userLeaderDao.getByUserIdAndLeaderId(userId, leaderId);
	}

}
