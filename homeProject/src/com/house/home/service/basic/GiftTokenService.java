package com.house.home.service.basic;

import java.util.Map;

import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.GiftToken;

public interface GiftTokenService extends BaseService {
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, GiftToken giftToken);
	
	public Map<String,Object> existTokenNo(String tokenNo);
}
