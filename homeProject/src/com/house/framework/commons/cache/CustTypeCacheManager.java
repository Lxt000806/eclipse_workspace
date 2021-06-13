package com.house.framework.commons.cache;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.house.framework.commons.conf.CommonConstant;
import com.house.home.entity.basic.CustType;
import com.house.home.service.basic.CustTypeService;

@Component
public class CustTypeCacheManager extends SimpleCacheManager {
	
	private static final Logger logger = LoggerFactory.getLogger(CustTypeCacheManager.class);
	
	@Autowired
	private CustTypeService custTypeService;
	
	public String getCacheKey() {
		return CommonConstant.CACHE_CUSTTYPE_KEY;
	}
	@Override
	public void initCacheData() {
		logger.info("初始化客户类型数据开始");
		List<CustType> listCustType =custTypeService.findByAllCustType();
		for (CustType custType : listCustType){
			this.put(custType.getCode(), custType);
		}
		this.put("custType",listCustType);
		logger.info("初始化客户类型数据结束");
	}
}
