package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.TopicFolderViewRole;

public interface TopicFolderViewRoleService extends BaseService {
	
	/**
	 * 加载所有的文档目录
	 * @param topicFolderViewRole
	 * @return
	 */
	public List<Map<String, Object>> getFolderViewRole(TopicFolderViewRole topicFolderViewRole);
	
	/**
	 * 根据folderPK获取查看角色
	 * @param folderPK
	 * @return
	 */
	public List<Map<String, Object>> getFolderViewRoleByFolderPK(Integer folderPK);
}
