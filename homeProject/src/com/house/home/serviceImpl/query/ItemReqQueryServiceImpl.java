package com.house.home.serviceImpl.query;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.ItemReqQueryDao;
import com.house.home.entity.insales.ItemReq;
import com.house.home.service.query.ItemReqQueryService;

@SuppressWarnings("serial")
@Service
public class ItemReqQueryServiceImpl extends BaseServiceImpl implements
		ItemReqQueryService {
	@Autowired
	private ItemReqQueryDao itemReqQueryDao;
	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, ItemReq itemReq) {
		// TODO Auto-generated method stub
		return itemReqQueryDao.findPageBySql(page, itemReq);
	}

}
