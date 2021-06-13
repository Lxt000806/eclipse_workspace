package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ItemCheckDao;
import com.house.home.entity.project.ItemCheck;
import com.house.home.service.project.ItemCheckService;

@SuppressWarnings("serial")
@Service
public class ItemCheckServiceImpl extends BaseServiceImpl implements ItemCheckService {

	@Autowired
	private ItemCheckDao itemCheckDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCheck itemCheck){
		return itemCheckDao.findPageBySql(page, itemCheck);
	}

	@Override
	public boolean isCheckItem(String custCode, String itemType1) {
		// TODO Auto-generated method stub
		return itemCheckDao.isCheckItem(custCode,itemType1);
	}

}
