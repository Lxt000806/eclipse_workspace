package com.house.home.serviceImpl.basic;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.framework.commons.orm.Page;
import com.house.home.dao.basic.DiscAmtCtrlDao;
import com.house.home.entity.basic.DiscAmtCtrl;
import com.house.home.service.basic.DiscAmtCtrlService;

@SuppressWarnings("serial")
@Service
public class DiscAmtCtrlServiceImpl extends BaseServiceImpl implements
		DiscAmtCtrlService {
	@Autowired
	private DiscAmtCtrlDao discAmtCtrlDao;

	@Override
	public Page<Map<String, Object>> findPageBySql(
			Page<Map<String, Object>> page, DiscAmtCtrl discAmtCtrl) {
		return discAmtCtrlDao.findPageBySql(page, discAmtCtrl);
	}
}
