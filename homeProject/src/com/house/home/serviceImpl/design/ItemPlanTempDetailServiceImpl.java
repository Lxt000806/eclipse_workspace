package com.house.home.serviceImpl.design;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.design.ItemPlanTempDetailDao;
import com.house.home.entity.design.ItemPlanTempDetail;
import com.house.home.service.design.ItemPlanTempDetailService;

@SuppressWarnings("serial")
@Service
public class ItemPlanTempDetailServiceImpl extends BaseServiceImpl implements ItemPlanTempDetailService {

	@Autowired
	private ItemPlanTempDetailDao itemPlanTempDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanTempDetail itemPlanTempDetail){
		return itemPlanTempDetailDao.findPageBySql(page, itemPlanTempDetail);
	}

}
