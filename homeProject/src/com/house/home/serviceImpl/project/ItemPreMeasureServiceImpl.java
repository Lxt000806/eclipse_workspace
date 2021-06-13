package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ItemPreMeasureDao;
import com.house.home.entity.project.ItemPreMeasure;
import com.house.home.service.project.ItemPreMeasureService;

@SuppressWarnings("serial")
@Service
public class ItemPreMeasureServiceImpl extends BaseServiceImpl implements ItemPreMeasureService {

	@Autowired
	private ItemPreMeasureDao itemPreMeasureDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPreMeasure itemPreMeasure){
		return itemPreMeasureDao.findPageBySql(page, itemPreMeasure);
	}

	@Override
	public int getMessageCount(String supplCode) {
		return itemPreMeasureDao.getMessageCount(supplCode);
	}

	@Override
	public Map<String, Object> getByPk(Integer pk) {
		return itemPreMeasureDao.getByPk(pk);
	}

	@Override
	public int getPrjJobMessageCount(String supplCode) {
		return itemPreMeasureDao.getPrjJobMessageCount(supplCode);
	}

}
