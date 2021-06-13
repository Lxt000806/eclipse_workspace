package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.RepositoryEvt;
import com.house.home.dao.basic.RepositoryDao;
import com.house.home.service.basic.RepositoryService;

@SuppressWarnings("serial")
@Service
public class RepositoryServiceImpl extends BaseServiceImpl implements RepositoryService {

	@Autowired
	private RepositoryDao repositoryDao;

	@Override
	public Page<Map<String, Object>> getGuideTopicList(Page<Map<String, Object>> page, RepositoryEvt evt) {
		return repositoryDao.getGuideTopicList(page, evt);
	}
	
	@Override
	public Page<Map<String, Object>> getDocList(Page<Map<String, Object>> page, RepositoryEvt evt) {
		return repositoryDao.getDocList(page, evt);
	}

	@Override
	public List<Map<String, Object>> getDocAttachmentList(Integer pk) {
		return repositoryDao.getDocAttachmentList(pk);
	}
	
	@Override
	public Page<Map<String, Object>> getGuideTopicListForWorker(Page<Map<String, Object>> page, RepositoryEvt evt) {
		return repositoryDao.getGuideTopicListForWorker(page, evt);
	}
	
	@Override
	public Page<Map<String, Object>> getDocListForWorker(Page<Map<String, Object>> page, RepositoryEvt evt) {
		return repositoryDao.getDocListForWorker(page, evt);
	}
}
