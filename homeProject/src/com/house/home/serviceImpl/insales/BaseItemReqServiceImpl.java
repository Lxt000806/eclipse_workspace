package com.house.home.serviceImpl.insales;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.insales.BaseItemReqDao;
import com.house.home.entity.insales.BaseItemReq;
import com.house.home.service.insales.BaseItemReqService;

@SuppressWarnings("serial")
@Service
public class BaseItemReqServiceImpl extends BaseServiceImpl implements BaseItemReqService {

	@Autowired
	private BaseItemReqDao baseItemReqDao;
	
	public Page<Map<String,Object>> findPageBySql(Page<Map<String,Object>> page, BaseItemReq baseItemReq){
		return baseItemReqDao.findPageBySql(page, baseItemReq);
	}

	@Override
	public Page<Map<String, Object>> findBaseItemReqList(
			Page<Map<String, Object>> page, BaseItemReq baseItemReq) {
		return baseItemReqDao.findBaseItemReqList(page, baseItemReq);
	}

}
