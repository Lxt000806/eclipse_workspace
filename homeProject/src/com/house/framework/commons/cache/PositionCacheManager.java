package com.house.framework.commons.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.home.entity.basic.Position;
import com.house.home.service.basic.PositionService;

@Component
public class PositionCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(PositionCacheManager.class);
	
	@Autowired
	private PositionService positionService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_POSITION_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化职位数据开始");
		List<Position> listPosition = positionService.findByNoExpired();
		for (Position position : listPosition){
			this.put(position.getCode(), position);
		}
		this.put("position",listPosition);
		logger.info("初始化职位代码数据结束");
	}
}
