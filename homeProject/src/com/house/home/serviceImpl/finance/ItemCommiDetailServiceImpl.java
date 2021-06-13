package com.house.home.serviceImpl.finance;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.finance.ItemCommiDetailDao;
import com.house.home.entity.finance.ItemCommiDetail;
import com.house.home.service.finance.ItemCommiDetailService;

@SuppressWarnings("serial")
@Service
public class ItemCommiDetailServiceImpl extends BaseServiceImpl implements ItemCommiDetailService {

	@Autowired
	private ItemCommiDetailDao itemCommiDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemCommiDetail itemCommiDetail){
		return itemCommiDetailDao.findPageBySql(page, itemCommiDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageBySql_khxx(
			Page<Map<String, Object>> page, ItemCommiDetail itemCommiDetail) {
		return itemCommiDetailDao.findPageBySql_khxx(page,itemCommiDetail);
	}

}
