package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppTempAreaDao;
import com.house.home.entity.insales.ItemAppTempArea;
import com.house.home.service.insales.ItemAppTempAreaService;

@SuppressWarnings("serial")
@Service
public class ItemAppTempAreaServiceImpl extends BaseServiceImpl implements ItemAppTempAreaService {

	@Autowired
	private ItemAppTempAreaDao itemAppTempAreaDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppTempArea itemAppTempArea){
		return itemAppTempAreaDao.findPageBySql(page, itemAppTempArea);
	}

}
