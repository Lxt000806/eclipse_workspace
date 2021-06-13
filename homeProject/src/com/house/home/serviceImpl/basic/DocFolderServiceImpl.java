package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.DocFolderDao;
import com.house.home.entity.basic.DocFolder;
import com.house.home.service.basic.DocFolderService;

@SuppressWarnings("serial")
@Service
public class DocFolderServiceImpl extends BaseServiceImpl implements DocFolderService {

	@Autowired
	private DocFolderDao docFolderDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DocFolder docFolder) {

		return docFolderDao.findPageBySql(page, docFolder);
	}
	
	@Override
	public List<DocFolder> getAll() {
		return docFolderDao.getAll();
	}
	
	@Override
	public List<DocFolder> getSubDocFolder(Integer parentPk){
		return docFolderDao.getSubDocFolder(parentPk);
	}
	
	@Override
	public Result doSaveProc(DocFolder docFolder) {
		return docFolderDao.doSaveProc(docFolder);
	}

	@Override
	public boolean hasDocByFolderPK(Integer folderPK) {
		return docFolderDao.hasDocByFolderPK(folderPK);
	}

}
