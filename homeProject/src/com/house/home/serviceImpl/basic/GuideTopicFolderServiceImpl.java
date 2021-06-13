package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.GuideTopicFolderDao;
import com.house.home.entity.basic.GuideTopicFolder;
import com.house.home.service.basic.GuideTopicFolderService;

@SuppressWarnings("serial")
@Service
public class GuideTopicFolderServiceImpl extends BaseServiceImpl implements GuideTopicFolderService {

	@Autowired
	private GuideTopicFolderDao guideTopicFolderDao;

	@Override
	public List<GuideTopicFolder> getAll() {
		return guideTopicFolderDao.getAll();
	}

	@Override
	public List<GuideTopicFolder> getSubGuideTopicFolder(Integer parentPk) {
		return guideTopicFolderDao.getSubGuideTopicFolder(parentPk);
	}

	@Override
	public Result doSaveProc(GuideTopicFolder guideTopicFolder) {
		return guideTopicFolderDao.doSaveProc(guideTopicFolder);
	}
	
	@Override
	public boolean hasGuideTopicByFolderPK(Integer folderPK) {
		return guideTopicFolderDao.hasGuideTopicByFolderPK(folderPK);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, GuideTopicFolder guideTopicFolder) {
		
		return guideTopicFolderDao.findPageBySql(page, guideTopicFolder);
	}
}
