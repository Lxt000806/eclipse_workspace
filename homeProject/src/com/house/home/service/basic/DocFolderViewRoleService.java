package com.house.home.service.basic;

import java.util.List;
import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.home.entity.basic.DocFolderViewRole;

public interface DocFolderViewRoleService extends BaseService {
	
	/**
	 * 加载所有的文档目录
	 * @param docFolderViewRole
	 * @return
	 */
	public List<Map<String, Object>> getDocFolderViewRole(DocFolderViewRole docFolderViewRole);
	
	/**
	 * 根据folderPK获取查看角色
	 * @param folderPK
	 * @return
	 */
	public List<Map<String, Object>> getDocFolderViewRoleByFolderPK(Integer folderPK);

}
