package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.ItemAppCtrlDao;
import com.house.home.entity.insales.ItemAppCtrl;
import com.house.home.service.insales.ItemAppCtrlService;

@SuppressWarnings("serial")
@Service
public class ItemAppCtrlServiceImpl extends BaseServiceImpl implements ItemAppCtrlService {

	@Autowired
	private ItemAppCtrlDao itemAppCtrlDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemAppCtrl itemAppCtrl){
		return itemAppCtrlDao.findPageBySql(page, itemAppCtrl);
	}

	@Override
	public ItemAppCtrl getByCustTypeAndItemType1(String custType,
			String itemType1) {
		return itemAppCtrlDao.getByCustTypeAndItemType1(custType,itemType1);
	}

}
