package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.RcvActDao;
import com.house.home.entity.basic.RcvAct;
import com.house.home.service.basic.RcvActService;

@SuppressWarnings("serial")
@Service
public class RcvActServiceImpl extends BaseServiceImpl implements RcvActService {
	
	@Autowired
	private RcvActDao rcvActDao;

	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, RcvAct rcvAct) {
		return rcvActDao.findPageBySql(page, rcvAct);
	}

	public RcvAct getByCode(String code) {
		return rcvActDao.getByCode(code);
	}

	@Override
	public RcvAct getByDescr(String descr, String code) {
		return rcvActDao.getByDescr(descr,code);
	}
	
}
