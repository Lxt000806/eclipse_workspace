package com.house.home.service.basic;

import java.util.List;
import java.util.Map;


import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.RepositoryEvt;

public interface RepositoryService extends BaseService {
	
	public Page<Map<String, Object>> getGuideTopicList(Page<Map<String, Object>> page, RepositoryEvt evt);

	public Page<Map<String, Object>> getDocList(Page<Map<String, Object>> page, RepositoryEvt evt);
	
	public List<Map<String, Object>> getDocAttachmentList(Integer pk);
	
	public Page<Map<String, Object>> getGuideTopicListForWorker(Page<Map<String, Object>> page, RepositoryEvt evt);

	public Page<Map<String, Object>> getDocListForWorker(Page<Map<String, Object>> page, RepositoryEvt evt);
}
