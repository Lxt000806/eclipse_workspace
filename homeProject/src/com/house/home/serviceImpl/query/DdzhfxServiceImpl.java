package com.house.home.serviceImpl.query;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.query.DdzhfxDao;
import com.house.home.entity.design.Customer;
import com.house.home.service.query.DdzhfxService;

@SuppressWarnings("serial")
@Service
public class DdzhfxServiceImpl extends BaseServiceImpl implements DdzhfxService {

	@Autowired
	private DdzhfxDao ddzhfxDao;
	
	public Page<Map<String, Object>> findPageBySqlTJFS(
			Page<Map<String, Object>> page, Customer customer,String orderBy,String direction) {
		return ddzhfxDao.findPageBySql_Ddtjfx_Tjfs(page, customer,orderBy,direction);
	}
	@Override
	public Map<String,Object> getbmqx(String czy) {
		return ddzhfxDao.getbmqx(czy);
	}
}
