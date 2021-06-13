package com.house.home.serviceImpl.commi;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.commi.ItemCommiCycleDao;
import com.house.home.entity.commi.ItemCommiCycle;
import com.house.home.service.commi.ItemCommiCycleService;

@SuppressWarnings("serial")
@Service
public class ItemCommiCycleServiceImpl extends BaseServiceImpl implements ItemCommiCycleService {

	@Autowired
	private ItemCommiCycleDao itemCommiCycleDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiCycle itemCommiCycle){
		return itemCommiCycleDao.findPageBySql(page, itemCommiCycle);
	}

	@Override
	public String checkStatus(String no) {
		return itemCommiCycleDao.checkStatus(no);
	}

	@Override
	public String isExistsPeriod(String no, String beginDate) {
		return itemCommiCycleDao.isExistsPeriod(no, beginDate);
	}

	@Override
	public void doComplete(String no) {
		itemCommiCycleDao.doComplete(no);
	}

	@Override
	public void doReturn(String no) {
		itemCommiCycleDao.doReturn(no);
	}

	@Override
	public Map<String, Object> doCount(ItemCommiCycle itemCommiCycle) {
		return itemCommiCycleDao.doCount(itemCommiCycle);
	}

	@Override
	public String getItemCommiNoByMon(Integer mon) {
		return itemCommiCycleDao.getItemCommiNoByMon(mon);
	}

}
