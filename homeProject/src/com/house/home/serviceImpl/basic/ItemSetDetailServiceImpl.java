package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.ItemSetDetailDao;
import com.house.home.entity.basic.ItemSetDetail;
import com.house.home.service.basic.ItemSetDetailService;

@SuppressWarnings("serial")
@Service
public class ItemSetDetailServiceImpl extends BaseServiceImpl implements ItemSetDetailService {

	@Autowired
	private ItemSetDetailDao itemSetDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemSetDetail itemSetDetail){
		return itemSetDetailDao.findPageBySql(page, itemSetDetail);
	}

}
