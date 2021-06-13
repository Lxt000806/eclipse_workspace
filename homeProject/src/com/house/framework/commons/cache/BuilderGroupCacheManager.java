package com.house.framework.commons.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.home.entity.basic.BuilderGroup;
import com.house.home.service.basic.BuilderGroupService;

@Component
public class BuilderGroupCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(BuilderGroupCacheManager.class);
	
	@Autowired
	private BuilderGroupService builderGroupService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_BUILDERGROUP_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化项目大类数据开始");
		List<BuilderGroup> listBuilderGroup = builderGroupService.findByNoExpired();
		for (BuilderGroup builderGroup : listBuilderGroup){
			this.put(builderGroup.getCode(), builderGroup);
		}
		logger.info("初始化项目大类数据结束");
	}
}
