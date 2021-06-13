package com.house.home.serviceImpl.mall;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.mall.MallBaseDao;
import com.house.home.service.mall.MallBaseService;


@SuppressWarnings("serial")
@Service
public class MallBaseServiceImpl extends BaseServiceImpl implements MallBaseService{

	@Autowired
	private MallBaseDao mallBaseDao;
	
	@Override
	public List<Map<String, Object>> getWorkType12List() {
		return mallBaseDao.getWorkType12List();
	}

}
