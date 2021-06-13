package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemPicDao;
import com.house.home.entity.basic.ItemPic;
import com.house.home.service.basic.ItemPicService;

@SuppressWarnings("serial")
@Service
public class ItemPicServiceImpl extends BaseServiceImpl implements ItemPicService {

	@Autowired
	private ItemPicDao itemPicDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPic itemPic){
		return itemPicDao.findPageBySql(page, itemPic);
	}
	@Override
	public int getCountNum(String itemCode) {
		return itemPicDao.getCountNum(itemCode);
	}

}
