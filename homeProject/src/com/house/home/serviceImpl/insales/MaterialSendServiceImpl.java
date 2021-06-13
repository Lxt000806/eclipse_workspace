package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.MaterialSendDao;
import com.house.home.entity.insales.ItemApp;
import com.house.home.service.insales.MaterialSendService;

@SuppressWarnings("serial")
@Service
public class MaterialSendServiceImpl extends BaseServiceImpl implements MaterialSendService {

	@Autowired
	private MaterialSendDao materialSendDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, ItemApp itemApp){
		return materialSendDao.findPageBySql(page, itemApp);
	}
}
