package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.basic.DocFolderViewRoleDao;
import com.house.home.entity.basic.DocFolderViewRole;
import com.house.home.service.basic.DocFolderViewRoleService;

@SuppressWarnings("serial")
@Service
public class DocFolderViewRoleServiceImpl extends BaseServiceImpl implements DocFolderViewRoleService {

	@Autowired
	private DocFolderViewRoleDao docFolderViewRoleDao;

	@Override
	public List<Map<String, Object>> getDocFolderViewRoleByFolderPK(Integer folderPK){
		return docFolderViewRoleDao.getDocFolderViewRoleByFolderPK(folderPK);
	}
	
	@Override
	public List<Map<String, Object>> getDocFolderViewRole(
			DocFolderViewRole docFolderViewRole) {
		return docFolderViewRoleDao.getDocFolderViewRole(docFolderViewRole);
	}
}
