package com.house.home.service.basic;

import java.util.List;
import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.GuideTopic;

public interface GuideTopicService extends BaseService{

	public Page<Map<String, Object>>  findPageBySql(Page<Map<String,Object>> page, GuideTopic guideTopic, boolean hasAuthByCzybh);

	public List<Map<String, Object>> getAuthNode(String czybm, boolean hasAuthByCzybh);

	public boolean getCheckTopic(String topic, Integer pk);
}
