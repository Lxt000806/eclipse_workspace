package com.house.framework.commons.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.home.entity.basic.Builder;
import com.house.home.service.basic.BuilderService;

@Component
public class BuilderCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(BuilderCacheManager.class);
	
	@Autowired
	private BuilderService builderService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_BUILDER_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化项目名称数据开始");
		List<Builder> listBuilder = builderService.findByNoExpired("where expired<>'T' ");
		for (Builder builder : listBuilder){
			this.put(builder.getCode(), builder);
		}
		logger.info("初始化项目名称数据结束");
	}
}
