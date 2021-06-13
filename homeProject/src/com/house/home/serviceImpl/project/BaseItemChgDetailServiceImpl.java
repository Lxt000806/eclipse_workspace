package com.house.home.serviceImpl.project;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.project.BaseItemChgDetailDao;
import com.house.home.entity.project.BaseItemChgDetail;
import com.house.home.service.project.BaseItemChgDetailService;

@SuppressWarnings("serial")
@Service
public class BaseItemChgDetailServiceImpl extends BaseServiceImpl implements BaseItemChgDetailService {

	@Autowired
	private BaseItemChgDetailDao baseItemChgDetailDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemChgDetail baseItemChgDetail){
		return baseItemChgDetailDao.findPageBySql(page, baseItemChgDetail);
	}

	@Override
	public Page<Map<String, Object>> findPageByNo(
			Page<Map<String, Object>> page, String id) {
		return baseItemChgDetailDao.findPageByNo(page,id);
	}

}
