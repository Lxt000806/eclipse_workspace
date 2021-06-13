package com.house.home.service.basic;

import java.util.Map;
import com.house.framework.commons.orm.BaseService;
import com.house.framework.commons.orm.Page;
import com.house.home.entity.basic.Advert;

public interface AdvertService extends BaseService {

	public Page<Map<String,Object>> getAdvertList(Page<Map<String,Object>> page, Advert advert);
	 
	public Map<String, Object> getAdvertDetail(Integer pk);
}
