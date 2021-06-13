package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.BaseItemTempDetailDao;
import com.house.home.entity.insales.BaseItemTempDetail;
import com.house.home.service.insales.BaseItemTempDetailService;

@SuppressWarnings("serial")
@Service
public class BaseItemTempDetailServiceImpl extends BaseServiceImpl implements BaseItemTempDetailService {

	@Autowired
	private BaseItemTempDetailDao baseItemTempDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemTempDetail baseItemTempDetail){
		return baseItemTempDetailDao.findPageBySql(page, baseItemTempDetail);
	}

}
