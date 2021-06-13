package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.PdcyfxDao;
import com.house.home.entity.insales.ItemBatchHeader;
import com.house.home.entity.insales.ItemPlanLog;
import com.house.home.service.insales.PdcyfxService;


@SuppressWarnings("serial")
@Service
public class PdcyfxServiceImpl extends BaseServiceImpl implements PdcyfxService {
	
	@Autowired
	private  PdcyfxDao pdcyfxDao ;

	@Override
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemBatchHeader itemBatchHeader){
		return  pdcyfxDao.findPageBySql(page,itemBatchHeader);
	}

}
