package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.BaseItemPlanBakDao;
import com.house.home.entity.design.BaseItemPlanBak;
import com.house.home.service.design.BaseItemPlanBakService;

@SuppressWarnings("serial")
@Service
public class BaseItemPlanBakServiceImpl extends BaseServiceImpl implements BaseItemPlanBakService {

	@Autowired
	private BaseItemPlanBakDao baseItemPlanBakDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemPlanBak baseItemPlanBak){
		return baseItemPlanBakDao.findPageBySql(page, baseItemPlanBak);
	}

}
