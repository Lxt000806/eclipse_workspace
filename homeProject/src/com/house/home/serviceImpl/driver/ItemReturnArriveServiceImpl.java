package com.house.home.serviceImpl.driver;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.driver.ItemReturnArriveDao;
import com.house.home.entity.driver.ItemReturnArrive;
import com.house.home.service.driver.ItemReturnArriveService;

@SuppressWarnings("serial")
@Service
public class ItemReturnArriveServiceImpl extends BaseServiceImpl implements ItemReturnArriveService {

	@Autowired
	private ItemReturnArriveDao itemReturnArriveDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemReturnArrive itemReturnArrive){
		return itemReturnArriveDao.findPageBySql(page, itemReturnArrive);
	}

	@Override
	public List<Map<String, Object>> findByReturnNo(String returnNo) {
		// TODO Auto-generated method stub
		return itemReturnArriveDao.findByReturnNo(returnNo);
	}

	@Override
	public Page<Map<String, Object>> findArriveByNo(
			Page<Map<String, Object>> page, ItemReturnArrive itemReturnArrive) {
		return itemReturnArriveDao.findArriveByNo(page, itemReturnArrive);
	}

	

}
