package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.ItemConfirmInformDao;
import com.house.home.entity.project.ItemConfirmInform;
import com.house.home.service.project.ItemConfirmInformService;

@SuppressWarnings("serial")
@Service
public class ItemConfirmInformServiceImpl extends BaseServiceImpl implements ItemConfirmInformService {

	@Autowired
	private ItemConfirmInformDao itemConfirmInformDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemConfirmInform itemConfirmInform){
		return itemConfirmInformDao.findPageBySql(page, itemConfirmInform);
	}

}
