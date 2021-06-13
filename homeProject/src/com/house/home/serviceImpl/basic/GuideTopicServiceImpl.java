package com.house.home.serviceImpl.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.house.home.dao.basic.GuideTopicDao;
import com.house.home.entity.basic.GuideTopic;
import com.house.home.service.basic.GuideTopicService;

@SuppressWarnings("serial")
@Service 
public class GuideTopicServiceImpl extends BaseServiceImpl implements GuideTopicService {
	@Autowired
	private  GuideTopicDao guideTopicDao;
	
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, GuideTopic guideTopic, boolean hasAuthByCzybh) {
		return guideTopicDao.findPageBySql(page, guideTopic, hasAuthByCzybh);
	}

	@Override
	public List<Map<String, Object>> getAuthNode(String czybm, boolean hasAuthByCzybh) {

		return guideTopicDao.getAuthNodeList(czybm, hasAuthByCzybh);
	}

	@Override
	public boolean getCheckTopic(String topic, Integer pk) {

		return guideTopicDao.getCheckTopic(topic, pk);
	}
	
	
}
