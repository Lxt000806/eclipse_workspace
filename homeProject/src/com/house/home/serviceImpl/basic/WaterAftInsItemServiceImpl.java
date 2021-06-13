package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.bean.Result;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.WaterAftInsItemEvt;
import com.house.home.dao.basic.WaterAftInsItemDao;
import com.house.home.entity.basic.WaterAftInsItem;
import com.house.home.service.basic.WaterAftInsItemService;

@SuppressWarnings("serial")
@Service
public class WaterAftInsItemServiceImpl extends BaseServiceImpl implements WaterAftInsItemService {

	@Autowired
	private WaterAftInsItemDao waterAftInsItemDao;
	
	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WaterAftInsItem waterAftInsItem){
		return waterAftInsItemDao.findPageBySql(page, waterAftInsItem);
	}
	
	@Override
	public Page<Map<String,Object>> findPageBySqlForDetail(Page<Map<String,Object>> page, WaterAftInsItem waterAftInsItem){
		return waterAftInsItemDao.findPageBySqlForDetail(page, waterAftInsItem);
	}
	
	@Override
	public Result saveForProc(WaterAftInsItem waterAftInsItem, String xml){
		return waterAftInsItemDao.saveForProc(waterAftInsItem,xml);
	}


	@Override
	public Page<Map<String, Object>> getWaterAftInsItemType2List(
			Page<Map<String, Object>> page) {
		return waterAftInsItemDao.getWaterAftInsItemType2List(page);
	}


	@Override
	public Page<Map<String, Object>> getWaterAftInsItemDetailList(
			Page<Map<String, Object>> page, WaterAftInsItemEvt evt) {
		return waterAftInsItemDao.getWaterAftInsItemDetailList(page, evt);
	}

	@Override
	public Result saveWaterAftInsItemAppForProc(String custCode,
			String workerCode, String xml) {
		return waterAftInsItemDao.saveWaterAftInsItemAppForProc(custCode,workerCode,xml);
	}
	
	@Override
	public Page<Map<String,Object>> getWaterAftInsItemList(Page<Map<String,Object>> page, WaterAftInsItemEvt evt){
		return waterAftInsItemDao.getWaterAftInsItemList(page, evt);
	}

}
