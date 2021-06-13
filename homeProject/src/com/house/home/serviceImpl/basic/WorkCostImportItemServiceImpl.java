package com.house.home.serviceImpl.basic;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.WorkCostImportItemDao;
import com.house.home.entity.basic.WorkCostImportItem;
import com.house.home.service.basic.WorkCostImportItemService;

@SuppressWarnings("serial")
@Service
public class WorkCostImportItemServiceImpl extends BaseServiceImpl implements WorkCostImportItemService {

	@Autowired
	private WorkCostImportItemDao workCostImportItemDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, WorkCostImportItem workCostImportItem){
		return workCostImportItemDao.findPageBySql(page, workCostImportItem);
	}

}
