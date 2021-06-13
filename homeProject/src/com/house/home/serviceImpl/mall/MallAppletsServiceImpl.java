package com.house.home.serviceImpl.mall;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.client.service.evt.MallAppletsEvt;
import com.house.home.dao.mall.MallAppletsDao;
import com.house.home.service.mall.MallAppletsService;

@SuppressWarnings("serial")
@Service
public class MallAppletsServiceImpl extends BaseServiceImpl implements MallAppletsService {
	
	@Autowired
	private MallAppletsDao mallAppletsDao;

	@Override
	public Page<Map<String, Object>> getWorkerList(Page<Map<String, Object>> page, MallAppletsEvt mallAppletsEvt) {
		return mallAppletsDao.getWorkerList(page, mallAppletsEvt);
	}

}
