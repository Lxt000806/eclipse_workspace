package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.BaseItemToCheckItemDao;
import com.house.home.entity.basic.BaseItemToCheckItem;
import com.house.home.service.basic.BaseItemToCheckItemService;

@SuppressWarnings("serial")
@Service 
public class BaseItemToCheckItemServiceImpl extends BaseServiceImpl implements BaseItemToCheckItemService {
	@Autowired
	private  BaseItemToCheckItemDao baseItemToCheckItemDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page,
			BaseItemToCheckItem baseItemToCheckItem) {
		return baseItemToCheckItemDao.findPageBySql(page, baseItemToCheckItem);
	}

	@Override
	public boolean checkCode(String baseItemCode, String baseCheckItemCode) {
		return baseItemToCheckItemDao.checkCode(baseItemCode, baseCheckItemCode);
	}


}
