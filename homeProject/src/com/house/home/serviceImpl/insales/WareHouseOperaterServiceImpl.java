package com.house.home.serviceImpl.insales;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.WareHouseOperaterDao;
import com.house.home.entity.insales.WareHouseOperater;
import com.house.home.service.insales.WareHouseOperaterService;


@SuppressWarnings("serial")
@Service
public class WareHouseOperaterServiceImpl extends BaseServiceImpl implements WareHouseOperaterService {

	@Autowired
	private WareHouseOperaterDao wareHouseOperaterDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WareHouseOperater wareHouseOperater){
		return wareHouseOperaterDao.findPageBySql(page, wareHouseOperater);
	}

	@Override
	public Page<Map<String, Object>> findPageByCzy(
			Page<Map<String, Object>> page, String id) {
		return wareHouseOperaterDao.findPageByCzy(page,id);
	}

	@Override
	public List<Map<String, Object>> findByCzybh(String czybh) {
		return wareHouseOperaterDao.findByCzybh(czybh);
	}

}
