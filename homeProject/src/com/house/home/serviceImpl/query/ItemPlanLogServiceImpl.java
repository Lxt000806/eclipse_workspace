package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemPlanLogDao;
import com.house.home.entity.insales.ItemPlanLog;
import com.house.home.service.query.ItemPlanLogService;

@SuppressWarnings("serial")
@Service
public class ItemPlanLogServiceImpl extends BaseServiceImpl implements ItemPlanLogService {

	@Autowired
	private ItemPlanLogDao itemPlanLogDao ;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemPlanLog itemPlanLog){
		return  itemPlanLogDao.findPageBySql(page, itemPlanLog);
	}

}
