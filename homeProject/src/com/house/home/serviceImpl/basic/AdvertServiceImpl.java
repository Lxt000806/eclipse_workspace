package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.AdvertDao;
import com.house.home.entity.basic.Advert;
import com.house.home.service.basic.AdvertService;

@SuppressWarnings("serial")
@Service
public class AdvertServiceImpl extends BaseServiceImpl implements AdvertService {

	@Autowired
	private AdvertDao advertDao;

	@Override
	public Page<Map<String,Object>> getAdvertList(Page<Map<String,Object>> page, Advert advert){
		return advertDao.getAdvertList(page, advert);
	}
	
	@Override
	public Map<String, Object> getAdvertDetail(Integer pk){
		return advertDao.getAdvertDetail(pk);
	}
}
