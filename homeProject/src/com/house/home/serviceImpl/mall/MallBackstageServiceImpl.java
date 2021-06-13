package com.house.home.serviceImpl.mall;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.house.framework.commons.orm.BaseServiceImpl;
import com.house.home.dao.mall.MallBackstageDao;
import com.house.home.service.mall.MallBackstageService;

@SuppressWarnings("serial")
@Service
public class MallBackstageServiceImpl extends BaseServiceImpl implements MallBackstageService {
	
	@Autowired
	private MallBackstageDao mallBackstageDao;
}
