package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.MainItemSetDao;
import com.house.home.entity.basic.ItemSet;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.service.basic.MainItemSetService;

@Service
@SuppressWarnings("serial")
public class MainItemSetServiceImpl extends BaseServiceImpl implements MainItemSetService {

	@Autowired
	private MainItemSetDao mainItemSetDao;

	@Override
	public Page<Map<String, Object>> findDetailPageBySql(
			Page<Map<String, Object>> page, ItemSetDetail itemSetDetail) {
	    
		return mainItemSetDao.findDetailPageBySql(page, itemSetDetail);
	}

	@Override
	public Result doSaveByProcedure(ItemSet itemSet) {
		return mainItemSetDao.saveByProcedure(itemSet);
	}
	
}
 
