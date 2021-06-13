package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.TopicFolderViewRoleDao;
import com.house.home.entity.basic.TopicFolderViewRole;
import com.house.home.service.basic.TopicFolderViewRoleService;

@SuppressWarnings("serial")
@Service
public class TopicFolderViewRoleServiceImpl extends BaseServiceImpl implements TopicFolderViewRoleService {
	
	@Autowired
	private TopicFolderViewRoleDao topicFolderViewRoleDao;

	@Override
	public List<Map<String, Object>> getFolderViewRole(
			TopicFolderViewRole topicFolderViewRole) {
		return topicFolderViewRoleDao.getFolderViewRole(topicFolderViewRole);
	}

	@Override
	public List<Map<String, Object>> getFolderViewRoleByFolderPK(
			Integer folderPK) {
		return topicFolderViewRoleDao.getFolderViewRoleByFolderPK(folderPK);
	}

}
