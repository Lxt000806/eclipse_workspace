package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.ItemPlanBakDao;
import com.house.home.entity.design.ItemPlanBak;
import com.house.home.service.design.ItemPlanBakService;

@SuppressWarnings("serial")
@Service
public class ItemPlanBakServiceImpl extends BaseServiceImpl implements ItemPlanBakService {

	@Autowired
	private ItemPlanBakDao itemPlanBakDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanBak itemPlanBak){
		return itemPlanBakDao.findPageBySql(page, itemPlanBak);
	}

}
